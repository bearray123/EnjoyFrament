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
 * 实现了嵌套滚动parent协议的父容器：NestedScrollingParent3
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
     *
     * 这套协议的核心协作原理：
     * 分方向来看
     *  1）向上滑动
     *      parent.onNestedPreScroll(父先消费下，看自己能消费多少-看头部区域， 父把消费的距离值通过consumed[]这个数组丢给child，child结合父已消费的值和本次滑动的距离做差值后继续消费)
     *          ===》 child消费parent没消费完剩余的距离（一般直接通过内置的childHelper内搞定）
     *  2）向下滑动
     *      child先消费，当child消费完拉下到顶后再把剩余未消费的距离”回流“给父再消费 ===》parent.onNestedScroll(...dxUnconsumed, dyUnconsumed...)
     *          ===》 parent拿到child未消费完的距离来进行最后的消费
     *
     */


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

    /**
     *
     * 作用：子消费后剩余的“未消费滚动”回流给父。
     * 典型：下滑到子到顶了（不能再下滚），此时需父把 头部区域TopArea 放下来
     *
     * onNestedScroll重载的方法有2个
     * 2代比3代少一个参数consumed
     * 具体这两个重载方法的调用策略先判断parent是否实现了3代协议，是则直接调用3代的方法，否则调用2代的
     * 框架内部的伪代码：
     *  if (parent instanceof NestedScrollingParent3) {
     *     ...
     *     parent.onNestedScroll // 3代的方法
     *     ...
     *  } else {
     *      ...
     *      parent.onNestedScroll // 2代的方法
     *      ...
     *  }
     *
     *  dxConsumed：被child消费了的水平方向滑动距离
     *  dyConsumed：被child消费了的垂直方向滑动距离
     *  dxUnconsumed：未被child消费的水平滑动距离
     *  dyUnconsumed：未被child消费的垂直滑动距离
     *
     *  该方法主要是把child剩余未消费的距离给消费掉
     *
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        xlog("NestedScrollParent::: onNestedScroll() , 子未消费的 dyUnconsumed=$dyUnconsumed")
        // 处理下滑：下滑时一般都是child先消费，没消费完的再给parent处理
        // 消费child剩余未消费的距离，dyUnconsumed为负数代表下滑剩余未消费的，scrollY大于0说明父能够向下滑
        if (dyUnconsumed < 0 && scrollY > 0) {
            val giveBack = minOf(-dyUnconsumed, scrollY) // 取子未消费完的dy和当前偏移的scrollY最小值
            // ✅ 还原TopArea
            scrollBy(0, -giveBack) // 向下滑为负数，加上-
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed) // 这行去掉好像也能work！
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        // 本类实现的是3代的parent协议，所以这个2代的方法压根儿调用不到，是空实现即可！
    }

    // dy = lastY - y
    // dy ：向上滑动为正数，向下滑动为负数
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        xlog("NestedScrollParent::: dy=$dy, scrollY=$scrollY")
        // 大前提：按照目前页面结构和滑动规则，scrollY 一定是大于0的。因为页面拉到顶部后就无法再下滑了，此时scrollY = 0
        // 上滑时 scrollY在不断增加，最大值能到 collapseRange
        // 下滑时 scrollY在不断减少，最小值到0
        // 所以：0 <= scrollY <= collapseRange

        // 处理上滑：上滑一般都是parent先消费（要把头部区域移出去），parent消费完后把剩余的交给child来处理
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

        // dy < 0 ：下滑，计算逻辑同理， 这段逻辑是错误的，除非你想要的效果是下滑时先把头部区域下滑出来再下滑内容列表区域，
        // 但目前主流做法都是先下滑列表区域，等列表区域下拉到顶后再下滑头部区域
        // scrollY 只要大于0代表还可继续下滑
//        if (dy < 0 && scrollY > 0 /*&& scrollY <= collapseRange*/) {
//            val use = minOf(dy, collapseRange - scrollY)
//            xlog("NestedScrollParent::: 命中父的下滑，下滑距离=$use")
//            scrollBy(0, use)
//            consumed[1] += use // 告知“这段被父吃掉了”
//            return
//        }

        // 走到这里时，dy一定<0， scrollY = 0
        // scrollY = 0 时，父不要再处理了，此时父已恢复到原始问题，再往下滑动的话交给子来做
        xlog("NestedScrollParent::: ============父已消费完毕-【向上】 或 父目前不需要消费，先给子消费看看-【向下】，交给子处理！================")

    }

    // endRegion

}