package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2023/3/11.
 */
class NewUserLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
        LayoutInflater.from(context).inflate(R.layout.test_merge_new_user_layout, this) as ConstraintLayout
        this.clipChildren
        // ViewGroup默认是不会执行onDraw的（性能优化），自定义ViewGroup需要强制走onDraw则需要执行setWillNotDraw(false)
        setWillNotDraw(false)

        setBackgroundColor(Color.BLUE)
        xlog("View::lifecycle:NewUserLayout: constructor()")
    }

    // region 绘制相关
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("View::lifecycle:NewUserLayout: onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("View::lifecycle:NewUserLayout: onLayout()")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        xlog("View::lifecycle:NewUserLayout: draw()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("View::lifecycle:NewUserLayout: onDraw()  measureWidth = $measuredWidth, mesureHeight=$measuredHeight")
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        xlog("View::lifecycle:NewUserLayout: dispatchDraw()")
    }
    // region end

    // region 触摸相关
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        xlog("View::lifecycle:NewUserLayout: onTouchEvent()")
//        return super.onTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        xlog("View::lifecycle:NewUserLayout: onInterceptTouchEvent()")
        return super.onInterceptTouchEvent(ev)
//        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        xlog("View::lifecycle:NewUserLayout: dispatchTouchEvent()")
        return super.dispatchTouchEvent(ev)
    }
    // region end

    // region view挂载相关
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        xlog("View::lifecycle:NewUserLayout: onAttachedToWindow()")
        parent.requestDisallowInterceptTouchEvent(true)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        xlog("View::lifecycle:NewUserLayout: onDetachedFromWindow()")
    }
    // region end


    override fun onFinishInflate() {
        super.onFinishInflate()
        xlog("View::lifecycle:NewUserLayout: onFinishInflate()")
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        xlog("View::lifecycle:NewUserLayout: onScrollChanged()")
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        xlog("View::lifecycle:NewUserLayout: onSizeChanged()")
        super.onSizeChanged(w, h, oldw, oldh)
    }



}