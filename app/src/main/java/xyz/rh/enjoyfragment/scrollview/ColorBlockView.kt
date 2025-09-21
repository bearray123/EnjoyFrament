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

    private var blockName: String? = null

    init {
        xlog("ColorBlockView:: <init> 构造器执行，attrs = ${attrs.toString()}")
        for (i in 0 until attrs!!.attributeCount) {
            xlog("ColorBlockView:: attrs Name=${attrs.getAttributeName(i)}, attrs Value = ${attrs.getAttributeValue(i)}")
            if (attrs.getAttributeName(i).equals("contentDescription")) {
                blockName = attrs.getAttributeValue(i)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("ColorBlockView:: [${blockName}] onMeasure() 测量执行")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("ColorBlockView:: [${blockName}] onLayout() 布局执行")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("ColorBlockView:: [${blockName}] onDraw() 绘制执行")
    }


}