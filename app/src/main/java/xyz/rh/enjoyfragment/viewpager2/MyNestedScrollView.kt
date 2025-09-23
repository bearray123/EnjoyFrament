package xyz.rh.enjoyfragment.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/1/30.
 */
class MyNestedScrollView(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    private var header: View? = null
    private var tab: View? = null
    // 折叠的范围：就是头部banner区域高度，因为整个滚动容器向上滑动过程中把头部banner滚出屏幕后，它下面的tab就吸顶了！
    private var collapseRange = 0

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        // 你也可以在外面 setHeaderAndTab(...) 显式注入
        header = findViewById(R.id.banner_img)
        tab = findViewById(R.id.host_tab_layout)

        viewTreeObserver.addOnGlobalLayoutListener {
            collapseRange = header?.measuredHeight ?: 0
        }
    }

    // 只处理垂直轴的嵌套滚动
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    /**
     * 上滑优先“预消费”给父：用于折叠 Banner。
     * 注意：type 可能是 TOUCH 或 NON_TOUCH（fling）。
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy > 0) {
            val remain = collapseRange - scrollY
            if (remain > 0) {
                val use = minOf(dy, remain)
                scrollBy(0, use)
                consumed[1] = use   // ✅ 告知子：这段我吃了
                return
            }
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    /**
     * 子消费后剩余的“未消费滚动”回流给父。
     * 典型：下滑到子到顶了（不能再下滚），dyUnconsumed<0，父把 Banner 放下来。
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int,
        type: Int
    ) {
        if (dyUnconsumed < 0 && scrollY > 0) {
            val giveBack = minOf(-dyUnconsumed, scrollY)
            scrollBy(0, -giveBack)   // ✅ 还原 Banner
            return
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }

    /**
     * fling 协商：上甩且未折完 -> 父先接管，把 Banner 甩完；否则交给子。
     */
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        if (velocityY > 0 && scrollY < collapseRange) {
            fling(velocityY.toInt())   // ✅ 用 NSV 自己的 OverScroller
            return true   // 表示我吃了这次 fling
        }
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    /**
     * 子 fling 后的回调（一般可不覆写）。
     * 也可在这里处理“下甩且未完全展开”的情况，看业务是否需要父接管。
     */
    override fun onNestedFling(
        target: View, velocityX: Float, velocityY: Float, consumed: Boolean
    ): Boolean {
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }












    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        xlog("MyNestedScrollView:: onStartNestedScroll")
//        return super.onStartNestedScroll(child, target, axes)
        // 只参与垂直轴
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
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