package xyz.rh.enjoyframent.layoutparams

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.rh.common.dp
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.BaseActivity
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.fragment.view.VolumeView
import xyz.rh.enjoyframent.temp.TempTestKotlin
import kotlin.concurrent.thread

/**
 * 测试跟View相关的属性，ViewTreeObserver等监听
 * Created by rayxiong on 2023/3/11.
 */
class TestLayoutParamsActivity : BaseActivity() {


    private val addViewButton: Button by lazy {
        findViewById(R.id.add_subview_btn)
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
        val viewStub = findViewById<ViewStub>(R.id.xiaorentou_container_view_stub)
        viewStub.visibility = View.VISIBLE

        addViewButton.setOnClickListener {
            val volumeView = VolumeView(this)
            xlog("test w::h ###only new View, before addView### :: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")
            topContainer.addView(volumeView)
//            Thread.sleep(3000)
            Handler().post {
                xlog("test w::h 123456")
            }

            testThreadId()

            xlog("test w::h ###after addView###, addViewButton.onClick:: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")

            volumeView.post {
                xlog("test w::h ###after volumeView.post### :: volumeView.measuredHeight=${volumeView.measuredHeight}, volumeView.measuredWidth=${volumeView.measuredWidth}, volumeView.height=${volumeView.height}, volumeView.width=${volumeView.width}")
            }
        }

    }

    override fun onResume() {
        super.onResume()

        testAddView()

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
    fun testViewTreeObserver() {
        val tx =  findViewById<TextView>(R.id.test_tx)

        tx.viewTreeObserver.addOnGlobalLayoutListener {
            xlog("textView-------------->addOnGlobalLayoutListener()")
        }
        tx.viewTreeObserver.addOnDrawListener {
            xlog("textView-------------->addOnDrawListener()")
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