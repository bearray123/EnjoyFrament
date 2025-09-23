package xyz.rh.enjoyfragment.touchevent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2023/5/2.
 */
class RHView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.setColor(Color.RED)
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
    }

    companion object {
        const val TAG = "RHView"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "draw lifecycle:: onMeasure 测量-> RHView")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "draw lifecycle:: onLayout 布局-> RHView")
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        Log.d(TAG, "draw lifecycle:: onDraw 绘制-> RHView")
        canvas?.save()
        xlog(TAG, "onDraw() ----> measuredWidth = $measuredWidth, measuredHeight = $measuredHeight, width = $width, height = $height")
        canvas?.drawCircle(20f*2, 20f*3, 20f, paint)
        paint.textSize = 30f // px

        // 画图形是从图形的left和top的位置开始往右下方向画
        // 画文字比较特殊，是从文字的左边和文字的baseline往右上方画，所以如果将文字画在0，0 的位置上，
        // 那么你就只能看到文字底部的一点点了，其实就是baseline下面的一点点内容，这时候y=0其实就是baseline了。
        // 那怎么才能将画出来的文字贴合屏幕呢？
        // 这就需要计算文字的最小包裹区域了，就是没有包含字间距和行间距的区域。
        // Paint提供了一个方法， getTextBounds()，传入一个Rect对象可以获得文字的左上右下（相对于左上角0，0位置）和小宽度。
        val text = "壹、贰、叁、肆、伍、陆、柒、捌、玖、拾"
        val rect = Rect()
        paint.getTextBounds(text, 0, 1, rect)

        // 这行代码是限制在画布上绘制时不超出视图的边界，如果不加这行代码绘制的东西是可以超出视图本身边界的~
        // Canvas默认不会限制绘制操作的边界，即使是超出了视图的实际尺寸。
        // 如果你希望确保在视图边界内绘制，可以使用 Canvas 的裁剪方法。例如，你可以在 onDraw 方法中使用 clipRect 方法，来限制绘制区域
        canvas?.clipRect(0,0,width, height)

        canvas?.drawText(text, 10f, (rect.height() - rect.bottom).toFloat(), paint)
        canvas?.restore()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        xlog(TAG, "onWindowFocusChanged()")
    }




}