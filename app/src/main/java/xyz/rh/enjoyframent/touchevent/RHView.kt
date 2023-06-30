package xyz.rh.enjoyframent.touchevent

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2023/5/2.
 */
class RHView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "RHView::"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog(TAG, "onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog(TAG, "onLayout()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog(TAG, "onDraw() ----> measuredWidth = $measuredWidth, measuredHeight = $measuredHeight, width = $width, height = $height")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        xlog(TAG, "onWindowFocusChanged()")
    }




}