package xyz.rh.enjoyfragment.nestedscroll

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * 实现了嵌套滚动协议的父容器：NestedScrollingParent3
 * Created by rayxiong on 2025/9/23.
 */
class NestedScrollParentRegion @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr), NestedScrollingParent3  {

    private val nestedScrollingParentHelper = NestedScrollingParentHelper(this)

    // 父滚动范围 0..topAreaHeight
    private var collapseRange = 0     // = 顶部可折叠高度 = topArea 高

    private val topArea: ViewGroup by lazy {
        findViewById(R.id.top_area)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 测完后才能拿到 topArea 的高度
        collapseRange = topArea.measuredHeight
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


    /** 夹住父滚动范围：0..collapseRange（滚完即“Sticky吸顶”） */
    override fun scrollTo(x: Int, y: Int) {
        val clampedY = y.coerceIn(0, collapseRange)
        if (clampedY != scrollY) super.scrollTo(x, clampedY)
    }


    // region 嵌套滚动协议：
    // 父协议基本上都是回调方法，都来自child的驱动

    /**
     * child.startNestedScroll()  --触发--> parent.onStartNestedScroll()
     *
     * 当前NestedScrollingChild调用方法startNestedScroll()时，会调用该方法。主要就是通过返回值告诉系统是否需要对后续的滚动进行处理
     * child: 该ViewParent的包含NestedScrollingChild的直接子View，如果只有一层嵌套，和target是同一个View
     * target: 本次嵌套滚动的NestedScrollingChild
     * nestedScrollAxes：滚动方向
     * @return
     *    true：表示我需要进行处理，后续的滚动会触发相应的回调
     *    false：表示我不需要处理，后面也就不会进行相应的回调了
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
//        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes)
//        return true
        // 只处理竖向
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    /**
     * 如果onStartNestedScroll()方法返回的是true的话，那么紧接着就会调用该方法. 它是让嵌套滚动在开始滚动之前让布局容器或者它的父类执行一些配置的初始化的
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        nestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
//        nestedScrollingParentHelper.onnestedS
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

    }

    // dy = lastY - y
    // dy ：向上滑动为正数，向下滑动为负数
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

        xlog("NestedScrollParent::: dy=$dy, scrollY=$scrollY")
//        if (topAreaCanShow(dy)) { // 若判断dy滑动后头部区域还能展示，那parent还要继续消费
//            xlog("NestedScrollParent::: onNestedPreScroll() 父要消费了，消费的dy=$dy")
//            consumed[1] = dy
//            scrollBy(0, dy)
//        } else { // 否则，说明经历dy后头部区域完全已经移除屏幕外了
//
//        }

        // 大前提：按照目前页面结构和滑动规则，scrollY 一定是大于0的。因为页面拉到顶部后就无法再下滑了，此时scrollY = 0
        // 上滑时 scrollY在不断增加，最大值能到 collapseRange
        // 下滑时 scrollY在不断减少，最小值到0
        // 所以：0 <= scrollY <= collapseRange

        // dy > 0 ：上滑
        if (dy > 0 && scrollY < collapseRange) {
            // collapseRange - scrollY 是可父可滑动的最大剩余距离，与dy对比取最小值
            // 比如：当前父剩余可滑动距离只有5px，但本次触摸滑动产生的dy是 6px，那最多只能滑动剩余的5px
            val use = minOf(dy, collapseRange - scrollY)
            xlog("NestedScrollParent::: 命中父的上滑，上滑距离=$use")
            // 父消费剩余可滑动距离，剩余的部分交给子
            scrollBy(0, use)
            // 告知子 “这段被父吃掉了” consume[1]代表Y轴
            consumed[1] += use
            return
        }

        // dy < 0 ：下滑，计算逻辑同理
        // scrollY 只要大于0代表还可继续下滑
        if (dy < 0 && scrollY > 0 /*&& scrollY <= collapseRange*/) {
            val use = minOf(dy, collapseRange - scrollY)
            xlog("NestedScrollParent::: 命中父的下滑，下滑距离=$use")
            scrollBy(0, use)
            consumed[1] += use // 告知“这段被父吃掉了”
            return
        }

        // 走到这里时，dy一定<0， scrollY = 0
        // scrollY = 0 时，父不要再处理了，此时父已恢复到原始问题，再往下滑动的话交给子来做
        xlog("NestedScrollParent::: ============父已消费完毕，交给子处理！================")

    }

    // endRegion


    /**
     * 头部区域还能展示吗？
     * 看当前滚动的距离来判断，如果当前滚动距离dy
     */
//    private fun topAreaCanShow(dy: Int): Boolean {
//        val maxScrollHeight = topArea.height // 外层父容器向上最大滚动距离：即topArea的高度
//        val topAreaCanScrollUp = topArea.canScrollVertically(-1) // 参数为负数代表向上滚动，整数为向下
//        xlog("NestedScrollParent::: topArea.height ==  ${topArea.height}, topAreaCanScrollUp=$topAreaCanScrollUp")
//        // dy > 0（上滑）
//        if (dy > 0 && scrollY > 0 && topAreaCanScrollUp) {
//            return true
//        }
//        return false
//    }



}