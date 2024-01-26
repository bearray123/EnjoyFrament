package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ActionMode
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2024/1/23.
 */
class MyConstraintLayout(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {


    // region 绘制相关
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("View::lifecycle:MyConstraintLayout: onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("View::lifecycle:MyConstraintLayout: onLayout()")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        xlog("View::lifecycle:MyConstraintLayout: draw()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("View::lifecycle:MyConstraintLayout: onDraw()  measureWidth = $measuredWidth, mesureHeight=$measuredHeight")
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        xlog("View::lifecycle:MyConstraintLayout: dispatchDraw()")
    }
    // region end

    // region 触摸相关
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        xlog("View::lifecycle:MyConstraintLayout: onTouchEvent()")
//        return super.onTouchEvent(event)

        // 对DOWN的诠释：当 ViewGroup 的 `onTouchEvent` 在序列的第一个事件（ACTION_DOWN）返回 `true`，
        // 这个 ViewGroup 就“捕获”了该序列的后续事件。这意味着，即使后续的触摸动作（如移动或抬起）发生在 ViewGroup 的范围之外，这些事件依然会被传递给它。
        if (event?.action == MotionEvent.ACTION_DOWN) {
            return true
        } else {
            val ret = super.onTouchEvent(event)
            xlog("View::lifecycle:MyConstraintLayout: onTouchEvent()------>ret=$ret")
            return ret
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        xlog("View::lifecycle:MyConstraintLayout: onInterceptTouchEvent()")
//        return super.onInterceptTouchEvent(ev)
        return true
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        xlog("View::lifecycle:MyConstraintLayout: dispatchTouchEvent()")
        return super.dispatchTouchEvent(ev)
    }

    // region end

    // region view挂载相关
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        xlog("View::lifecycle:MyConstraintLayout: onAttachedToWindow()")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        xlog("View::lifecycle:MyConstraintLayout: onDetachedFromWindow()")
    }
    // region end


    override fun onFinishInflate() {
        super.onFinishInflate()
        xlog("View::lifecycle:MyConstraintLayout: onFinishInflate()")
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        xlog("View::lifecycle:MyConstraintLayout: onScrollChanged()")
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        xlog("View::lifecycle:MyConstraintLayout: onSizeChanged()")
        super.onSizeChanged(w, h, oldw, oldh)
    }



}