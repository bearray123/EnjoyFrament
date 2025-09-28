package xyz.rh.enjoyfragment.nestedscroll

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

/**
 * 实现嵌套滚动child协议的容器，只要被它包裹着的子view就都不需要再实现这套协议了
 * Created by rayxiong on 2025/9/28.
 */
class NestedScrollChildWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) , NestedScrollingChild3{

    private val childHelper = NestedScrollingChildHelper(this)
    private val consumed = IntArray(2)
    private val offset = IntArray(2)
    private var lastY = 0f
    private var vt: VelocityTracker? = null

    init { isClickable = true; childHelper.isNestedScrollingEnabled = true }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.y
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
                vt = VelocityTracker.obtain().also { it.addMovement(event) }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = (lastY - event.y).toInt()
                vt?.addMovement(event)

                dispatchNestedPreScroll(0, dy, consumed, offset, ViewCompat.TYPE_TOUCH)
                val un = dy - consumed[1]
                dispatchNestedScroll(0, 0, 0, un, offset, ViewCompat.TYPE_TOUCH)

                lastY = event.y - offset[1]
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                vt?.addMovement(event); vt?.computeCurrentVelocity(1000)
                val vy = vt?.yVelocity ?: 0f
                val taken = dispatchNestedPreFling(0f, vy)
                if (!taken) dispatchNestedFling(0f, vy, false)
                stopNestedScroll(ViewCompat.TYPE_TOUCH)
                vt?.recycle(); vt = null
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    // —— helper 代理，都交给内置的helper来处理 —— //

    override fun setNestedScrollingEnabled(enabled: Boolean) { childHelper.isNestedScrollingEnabled = enabled }

    override fun isNestedScrollingEnabled() = childHelper.isNestedScrollingEnabled



    // Region start：嵌套滚动协议：
    // 嵌套滚动的这套协议的驱动着是child，以孩子为中心来驱动，可以发现child里基本上都是调度方法，类似dispatchXXX, startXXX
    // parent的协议里基本上是回调方法，类似于onXXX

    // 对于实现Parent和Child协议的两者不要求是直接父子关系。可以是爷孙等关系！
    // 实现了 NestedScrollingChild 的控件会自下而上沿着 ViewParent 链查找最近的
    // 实现了 NestedScrollingParent(2/3) 并且 onStartNestedScroll(...) 返回 true 的“父”（可能是父、爷爷、曾祖父…）。
    // 中间夹着普通容器（如 ConstraintLayout）完全没问题——它会被跳过。


    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return childHelper.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll(type: Int) {
        childHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return childHelper.hasNestedScrollingParent(type)
    }

    /**
     * 在当前View消费滚动距离之前把滑动距离传给父布局。相当于把优先处理权交给Parent
     * 内部一般也是直接代理给NestedScrollingChildHelper的同名方法即可。
     * dx: 当前水平方向滑动的距离
     * dy：当前垂直方向滑动的距离
     * consumed：输出参数，会将Parent消费掉的距离封装进该参数，consumed[0]代表水平方向，consumed[1]代表垂直方向
     * @return true：代表Parent消费了滚动距离
     */
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    /**
     * 当前view消费滚动距离之后。通过调用该方法，把剩下的滚动距离传给父布局。如果当前没有发生嵌套滚动，或者不支持嵌套滚动，调用该方法无效。
     * 内部一般也是直接代理给NestedScrollingChildHelper的同名方法即可。
     * dxConsumed：被当前view消费了的水平方向滑动距离
     * dyConsumed：被当前view消费了的垂直方向滑动距离
     * dxUnconsumed：未被消费的水平滑动距离
     * dyUnconsumed：未被消费的垂直滑动距离
     * offsetInWindow：输出可选参数。如果不是null，该方法完成返回时，会将该视图从该操作之前到该操作之后的本地视图坐标中的偏移量封装进该参数中，offsetInWindow[0]水平方向，offsetInWindow[1]垂直方向
     * @return true：标识滚动事件分发成功，false：分发失败
     */
    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    /**
     * 将惯性滑动的速度分发给Parent。内部一般也是直接代理给NestedScrollingChildHelper的同名方法即可
     * velocityX: 代表水平滑动速度
     * velocityY：代表垂直滑动速度
     * consumed：true 表示当前view消费了滑动事件，否则传false
     * @return true 表示Parent处理了滑动事件
     */
    override fun dispatchNestedFling(vx: Float, vy: Float, consumed: Boolean) = childHelper.dispatchNestedFling(vx, vy, consumed)

    /**
     * 在当前view自己处理惯性滑动前，先将滑动事件分发给Parent，一般来说如果想自己处理惯性的滑动事件，就不应该调用该方法给Parent处理。
     * 如果给了Parent并且返回true，那表示Parent已经处理了，自己就不应该再做处理。
     * 返回false，代表Parent没有处理，但是不代表Parent后面就不用处理了
     * @return true：表示Parent处理了滑动事件
     */
    override fun dispatchNestedPreFling(vx: Float, vy: Float) = childHelper.dispatchNestedPreFling(vx, vy)


}