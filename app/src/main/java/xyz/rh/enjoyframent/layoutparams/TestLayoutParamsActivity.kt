package xyz.rh.enjoyframent.layoutparams

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.View
import android.view.ViewStub
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.fragment.view.VolumeView
import xyz.rh.enjoyframent.temp.TempTestKotlin
import kotlin.concurrent.thread

/**
 * Created by rayxiong on 2023/3/11.
 */
class TestLayoutParamsActivity : AppCompatActivity() {


    private val addViewButton: Button by lazy {
        findViewById(R.id.add_subview_btn)
    }

    private val topContainer: ConstraintLayout by lazy {
        findViewById(R.id.top_container)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout_params_page_layout)


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


}