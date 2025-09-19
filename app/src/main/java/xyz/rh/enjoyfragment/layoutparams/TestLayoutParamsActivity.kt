package xyz.rh.enjoyfragment.layoutparams

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewStub
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Runnable
import xyz.rh.common.dp
import xyz.rh.common.getUiHandler
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R
import xyz.rh.enjoyfragment.fragment.view.VolumeView
import xyz.rh.enjoyfragment.temp.TempTestKotlin
import kotlin.concurrent.thread

/**
 * 测试跟View相关的属性，ViewTreeObserver等监听
 * Created by rayxiong on 2023/3/11.
 */
class TestLayoutParamsActivity : BaseActivity() {


    private var popupWindow: PopupWindow? = null

    private val addViewButton: Button by lazy {
        findViewById(R.id.add_subview_btn)
    }

    private val juxiaodiGif: ImageView by lazy {
        findViewById(R.id.juxiaodi_gif)
    }

    private val topContainer: ConstraintLayout by lazy {
        findViewById(R.id.top_container)
    }

    private val netImage1: ImageView by lazy {
        findViewById(R.id.net_image_1)
    }

    private val netImage2: ImageView by lazy {
        findViewById(R.id.net_image_1)
    }

    // 约束布局容器
    private val centerGroup: ConstraintLayout by lazy {
        findViewById(R.id.center_group)
    }

    // 线性布局容器
    private val linearGroup: LinearLayout by lazy {
        findViewById(R.id.linear_group)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout_params_page_layout)

        testViewTreeObserver()

        testMarquee()

        val test1 = TempTestKotlin()
        test1.testIns {
            xlog("test inline::: block call")
        }

        val newUserLayout = findViewById<NewUserLayout>(R.id.new_user_layout_container)
        testViewApis(newUserLayout) // 测试View类的一些api执行效果

        val viewStub = findViewById<ViewStub>(R.id.xiaorentou_container_view_stub)
        viewStub.visibility = View.VISIBLE

        /** 验证测量、布局等渲染管线，理解MeasureSpec **/
        testMeasureAndLayout(topContainer)

        addViewButton.setOnClickListener {
            xlog("test window ==== 1111 === ${addViewButton.windowToken}")
            val volumeView = VolumeView(this)
            xlog("test w::h ###only new View, before addView### :: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")
            topContainer.addView(volumeView)


            popupWindow?.dismiss()
            popupWindow = PopupWindow().apply {
                this.height = 30.dp
                this.width = 80.dp
                this.contentView = View(this@TestLayoutParamsActivity).apply {
                    this.setBackgroundColor(Color.BLUE)
                    this.setBackgroundResource(R.drawable.shape_round_corner_rect)
                    this.startAnimation(AnimationUtils.loadAnimation(this@TestLayoutParamsActivity, R.anim.popupwindow_scale_anim))
                    this.setOnClickListener {
                        xlog("PopupWindow被点击")
                        Toast.makeText(this@TestLayoutParamsActivity, "PopupWindow被点击",Toast.LENGTH_SHORT).show()
                    }
                }
//                this.enterTransition = R.styleable.Popupwindow_
            }
            popupWindow?.showAsDropDown(addViewButton)
            popupWindow?.contentView?.post {
                // 这里必须post不然拿不到windowtoken，因为还没绘制
                // 目前测出来的结论：popupWindow是独立的window！打印出来的windowToken与activity里view获取到的不一样！
                xlog("test window ==== 2222 === ${popupWindow?.contentView?.windowToken}")
            }

            testThreadId()

            xlog("test w::h ###after addView###, addViewButton.onClick:: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")

            volumeView.post {
                xlog("test w::h ###after volumeView.post### :: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")
            }
        }

        ////////// 测试100帧的juxiaodi gif是否卡顿
        Glide.with(this).load(/*R.drawable.juxiaodi_100frame*/"https://dpubstatic.udache.com/static/dpubimg/Ba8ykguX0P2m_jTimf97Z.gif")
            .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (resource is Animatable2Compat) {
                    resource.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            resource.unregisterAnimationCallback(this)
                            juxiaodiGif.visibility = View.GONE
                        }
                    })
                }
                when (resource) {
//                    is WebpDrawable -> {
//                        resource.loopCount = 1
//                        resource.start()
//                    }
                    is GifDrawable -> {
//                        resource.setLoopCount(1)
                        resource.start()
                    }
                }
//                startTopMaskingTvInAnim = Runnable{startTopMaskingTextInAnimotion(text)}
//                UiThreadHandler.postDelayed(startTopMaskingTvInAnim, 800)
                juxiaodiGif.setImageDrawable(resource)
                juxiaodiGif.visibility = View.VISIBLE
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })


//        val centerContainer = CenterContainer(this, centerGroup)

        // 以LinearLayout作为容器的话，
//        testMeasure(linearGroup)
        // 以ConstraintLayout作为容器的话，
        testMeasure(centerGroup)


    }

    /**
     * 测试 measure和layout
     * 如何在addView之前测量，理解MeasureSpec
     */
    private fun testMeasureAndLayout(rootView: ViewGroup) {
        rootView.post {
            xlog("test measureSpec:: topContainer 父 measureWidth=${rootView.measuredWidth}, measuredHeight=${rootView.measuredHeight}")
            val subView = TextView(this).apply {
                text = "这是一段文字，这是一段文字"
                layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, 51)
            }
//            val subView = CustomViewContainer(this) // 也可以换这个自定义view测试
            val pWMeasureSpec = View.MeasureSpec.makeMeasureSpec(rootView.measuredWidth, MeasureSpec.AT_MOST)
            val pHMeasureSpec = View.MeasureSpec.makeMeasureSpec(rootView.measuredHeight, MeasureSpec.AT_MOST)

            val widthUsed  = rootView.paddingLeft + rootView.paddingRight + subView.marginLeft + subView.marginRight
            val heightUsed = rootView.paddingTop  + rootView.paddingBottom + subView.marginTop  + subView.marginBottom

            xlog("test measureSpec::  widthUsed=$widthUsed, heightUsed=$heightUsed")
//
            val childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(pWMeasureSpec, widthUsed, subView.layoutParams.width)
            val childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(pHMeasureSpec, heightUsed, subView.layoutParams.height)


            subView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
            xlog("test measureSpec:: measureWidth=${subView.measuredWidth}, measuredHeight=${subView.measuredHeight}")
            xlog("test measureSpec:: width=${subView.width}, height=${subView.height}")

            rootView.addView(subView)
        }
    }


    private fun testMeasure(rootView: ViewGroup) {

        val centerContainer = CenterContainer(this, rootView)

        // EXACTLY: 精确模式，表示父布局已经决定了子视图的确切大小，子视图应该遵循这个大小。当使用EXACTLY模式时，你是在告诉子视图它的确切大小应该是多少，不管子视图的内容或内部布局是如何的。这通常用于当父视图已经决定了子视图的大小，子视图需要遵循这个大小。
        // UNSPECIFIED：未指定模式，表示父布局对子视图大小没有任何限制，子视图可以任意大。使用这个模式时，子视图会根据其内容和内部逻辑来决定最合适的大小。这在你想要让布局完全根据其内容来决定自己的大小时非常有用，比如当你不知道子视图需要多大空间时。
        // AT_MOST：最大模式，表示子视图最多只能达到指定大小，它可以是任何小于等于这个大小的值。这个模式适合在你想要设定一个上限，但允许视图根据其内容来选择更小的尺寸时使用。

        rootView.post { // post后 measuredHeight == height，用谁都一样
            xlog("CenterContainer::: 看看父容器 的Height = ${rootView.measuredHeight}")


            val parentWidthSpec = View.MeasureSpec.makeMeasureSpec(rootView.measuredWidth, MeasureSpec.AT_MOST)
            val parentHeightSpec = View.MeasureSpec.makeMeasureSpec(rootView.measuredHeight, MeasureSpec.AT_MOST)

            val widthUsed  = rootView.paddingLeft + rootView.paddingRight + centerContainer.rootView.marginLeft + centerContainer.rootView.marginRight
            val heightUsed = rootView.paddingTop  + rootView.paddingBottom + centerContainer.rootView.marginTop  + centerContainer.rootView.marginBottom

            val childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(parentWidthSpec, widthUsed, centerContainer.rootView.layoutParams.width)
            val childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(parentHeightSpec, heightUsed, centerContainer.rootView.layoutParams.height)
            centerContainer.rootView.measure(childWidthMeasureSpec, childHeightMeasureSpec)


//            centerContainer.rootView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            // 这里测量出来的好像都是父容器的宽高（EXACTLY和AT_MOST）?? 为啥？ 得用getChildMeasureSpec 才可以测出来child自身设置的宽高
//            centerContainer.rootView.measure(View.MeasureSpec.makeMeasureSpec(rootView.width, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(rootView.height, View.MeasureSpec.AT_MOST))
            xlog("CenterContainer::: 在addView之前看看高度，and getHeight = ${centerContainer.rootView.measuredHeight}")
        }

        centerContainer.rootView.post {
            xlog("CenterContainer::: post等待渲染完成看高度 and getHeight ===> in post block = ${centerContainer.rootView.measuredHeight}")
        }
    }


    private fun testViewApis(view: NewUserLayout) {

        getUiHandler().postDelayed(Runnable {
            xlog("test invalidate()::")
            // 结论：invalidate()操作只会导致重绘（一般来说是这样，除非布局也发生变化就另说了）
            view.invalidate()

        }, 5000)

        getUiHandler().postDelayed(Runnable {
            xlog("test requestLayout()::")
            // 结论：requestLayout操作会导致 onMeasure onLayout onDraw三部曲都执行
            view.requestLayout()

        }, 10_000)

        getUiHandler().postDelayed(Runnable {
            xlog("test scrollBy()::")
            // 结论：scrollBy等操作只会导致重绘，即onDraw，效果跟invalidate()一样，从定义上来看scrollBy只是改变了view的位置而已，其布局（布局是对它的children而言的）是没变化的，所以不会执行measure layout等操作
            view.scrollBy(40, 80)

        }, 20_000)

    }

    override fun onResume() {
        super.onResume()
//        testAddView()

    }



    // 测试验证 addView操作之后是不是可以立即拿到宽高，是否立即去绘制
    // 结论：无法立即拿到，addView只是给ViewGroup添加了childView，到绘制环节其实还是需要依赖vsync信号机制来刷新
    private fun testAddView() {
        Handler().postDelayed(Runnable {

            xlog("onResume:: topContainer ---> before add view ---》 topContainer.measuredHeight = ${topContainer.measuredHeight}")
            val subLayoutView = ConstraintLayout(this).apply {
                val newLayoutParams = ViewGroup.LayoutParams(100.dp, 300.dp)
                layoutParams = newLayoutParams // 设置vg的宽和高
                setBackgroundColor(Color.GREEN) // 设置vg的背景
            }

            xlog("only new view ---> view.measuredHeight=${subLayoutView.measuredHeight}, view.height=${subLayoutView.height}")

            // 利用未来的父view（当前还未执行addView）进行测量下，看是否可以拿到宽和高
//            subLayoutView.measure(
//                View.MeasureSpec.makeMeasureSpec(topContainer.measuredWidth, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(topContainer.measuredHeight, View.MeasureSpec.EXACTLY)
//            )
//            xlog("after measure ---> view.measuredHeight=${subLayoutView.measuredHeight}, view.height=${subLayoutView.height}")

            topContainer.addView( // 给这个容器动态添加一个子View，用于验证addView之后是否可以立即获取到宽和高
                subLayoutView
            )
//            Thread.sleep(3000)
//            xlog("onResume:: topContainer ---> after add view ---》 topContainer.measuredHeight = ${topContainer.measuredHeight}")
            topContainer.post {
                xlog("onResume:: topContainer ---> after post ---》 topContainer.measuredHeight = ${topContainer.measuredHeight}")
            }
        }, 5_000)
    }

    // 理解线程id在两个领域的区别：
    fun testThreadId() {
        // android.os.Process.myTid()是系统级的ID；Thread.currentThread().getId()是Java级的ID
        // 我们在Logcat的日志中看到系统打印的线程id其实是系统级的ID，和我们自己调用Thread.getId是不一样的
        // Thread.getId() 是 Thread类自己管理的一个静态ID，在调用构造函数时即生成，从1开始递增。

        xlog("test thread id:: Thread.currentThread id = ${Thread.currentThread().id}")
        // Looper里获取thread后去拿id其实一样，都是jvm级别的threadid
//    xprintln("test thread id:: Looper.myLooper().thread id = ${Looper.myLooper()?.thread?.id}")

        xlog("test thread id:: current process id = ${Process.myPid()}, uid = ${Process.myUid()}, tid = ${Process.myTid()}")

        thread(name = "thread-defined-123") {
            Thread.sleep(3000)
            xlog("test thread id::  thread-defined-123 delay 3s...")
            xlog("test thread id:: Thread.currentThread id = ${Thread.currentThread().id}")
            xlog("test thread id:: current process id = ${Process.myPid()}, uid = ${Process.myUid()}, tid = ${Process.myTid()}")
        }
    }

    // 测试viewTreeObserver
    private fun testViewTreeObserver() {
        val tx =  findViewById<TextView>(R.id.test_tx)

        tx.viewTreeObserver.addOnGlobalLayoutListener {
            xlog("textView-------------->addOnGlobalLayoutListener()")
        }
        tx.viewTreeObserver.addOnDrawListener {
            // 因为有小桔视的探头动画不停的重复播放，这里会不停地打印，去掉log
//            xlog("textView-------------->addOnDrawListener()")
        }

        tx.viewTreeObserver.addOnWindowAttachListener(object : ViewTreeObserver.OnWindowAttachListener {
            override fun onWindowAttached() {
                xlog("textView-------------->onWindowAttached()")
            }

            override fun onWindowDetached() {
                xlog("textView-------------->onWindowDetached()")
            }

        })
        tx.viewTreeObserver.addOnWindowFocusChangeListener {
            xlog("textView-------------->addOnWindowFocusChangeListener()")
        }

        tx.viewTreeObserver.addOnGlobalFocusChangeListener { oldFocus, newFocus ->
            xlog("textView-------------->addOnGlobalFocusChangeListener()")
        }
        tx.viewTreeObserver.addOnGlobalLayoutListener {
            xlog("textView-------------->addOnGlobalLayoutListener()")
        }
        tx.post {
            xlog("========== tx.y = ${tx.y} , tx.translationY = ${tx.translationY} , topContainer.y = ${topContainer.y}")
        }
    }

    fun testMarquee() {
        val marqueeText = findViewById<TextView>(R.id.test_marquee)
        marqueeText.text = "我靠，这个文案好长，好像是一行显示不下啊，怎么办，跑马灯"
        marqueeText.visibility = View.VISIBLE
        marqueeText.isSelected = true
    }


}