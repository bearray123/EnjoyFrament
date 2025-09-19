package xyz.rh.enjoyfragment.scrollview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2025/9/19.
 */
class ColorBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    init {

        xlog("ColorBlockView:: <init> 构造器执行，attrs = ${attrs.toString()}")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("ColorBlockView:: onMeasure() 测量执行")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("ColorBlockView:: onLayout() 布局执行")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("ColorBlockView:: onDraw() 绘制执行")
    }


}