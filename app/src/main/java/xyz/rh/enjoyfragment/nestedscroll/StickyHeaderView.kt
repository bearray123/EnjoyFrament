package xyz.rh.enjoyfragment.nestedscroll

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * Created by rayxiong on 2025/9/23.
 */

class StickyHeaderView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    init {
        paint.apply {
            strokeWidth = 2f // 画笔宽度
            setColor(Color.BLUE)
            isAntiAlias = true // 抗锯齿
            style = Paint.Style.FILL
            textSize = sp2px(20F)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.save()
//        canvas?.drawText("英雄列表", 0f, 0f, paint)
//        canvas?.restore()


        // drawText(x, y, ...) 里的 y 是“基线”(baseline) 位置，不是文字的顶部。
        // 大多数字体的 ascent 是负值，表示“从基线到文字顶部的距离”。当你传 y = 0 时，基线在画布最上沿，文字的可见部分会在 y = ascent（一个负数），也就是跑到画布上方被裁掉，于是看不到。
        val fm = paint.fontMetrics
//        val cx = paddingLeft.toFloat()
//        val cy = paddingTop - fm.ascent   // 用 -ascent 把顶部对齐到 paddingTop
        val cx = width / 2f // 水平居中，目前效果不是完全水平居中的，有些偏右
        val cy = height / 2f - (fm.ascent + fm.descent) / 2f // 垂直居中
        canvas?.drawText("英雄列表", cx, cy, paint)

    }

    private fun sp2px(sp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)


}