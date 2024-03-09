package xyz.rh.enjoyfragment.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2024/1/30.
 */
class MyNestedScrollView(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {



    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        xlog("MyNestedScrollView:: onNestedPreFling")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        xlog("MyNestedScrollView:: onNestedPreScroll")
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        xlog("MyNestedScrollView:: onStartNestedScroll")
        return super.onStartNestedScroll(child, target, axes)
    }

    override fun onStopNestedScroll(target: View) {
        xlog("MyNestedScrollView:: onStopNestedScroll")
        super.onStopNestedScroll(target)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        xlog("MyNestedScrollView:: dispatchTouchEvent --> event=${ev?.action}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        xlog("MyNestedScrollView:: onInterceptTouchEvent --> event=${ev.action}")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        xlog("MyNestedScrollView:: onTouchEvent --> event=${ev.action}")
        return super.onTouchEvent(ev)
    }



}