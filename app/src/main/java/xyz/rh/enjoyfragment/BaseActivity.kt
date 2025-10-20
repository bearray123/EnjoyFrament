package xyz.rh.enjoyfragment

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2023/8/9.
 */
open class BaseActivity : FragmentActivity() /*:AppCompatActivity*/ {

    companion object {
        const val TAG = "BaseActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        有两种布局模式：
//        “夹心模式”（默认）：系统把内容限制在系统栏（状态栏/导航栏）以内——内容不会盖到状态栏下面。
//        edge-to-edge：内容可以延伸到系统栏后面（你会看到内容“顶到屏幕顶部”），需要你自己用 WindowInsets 来给内容加合适的 padding/margin。
        // 如果把targetSdkVersion升级到35后，跑在api>=35的机器上默认行为就变成了edge-to-edge了：
        // 官方文档：https://developer.android.com/develop/ui/views/layout/insets?hl=zh-cn
        // 摘录核心点：使内容从屏幕顶部边缘延伸到底部边缘。这是自 Android 15（API 级别 35）开始的默认行为。这意味着，应用的顶部和底部区域布局在状态栏和导航栏后面。状态栏和导航栏统称为系统栏。系统栏是通常专用于显示通知、传达设备状态和进行设备导航的区域。


        //  edge-to-edge : 沉浸式状态栏，背景需要透明
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT


        val contentView = findViewById<View>(R.id.content) // FrameLayout
        // DecorView extends FrameLayout
        // 对Activity、Window、DecorView的总结：
        //
        xlog("BaseActivity::Lifecycle::onCreate --->[ this = $this ], [window=$window], [window.decorView = ${window.decorView}] , findViewById(R.id.content) = $contentView")

    }


    override fun onStart() {
        super.onStart()
        xlog("BaseActivity::Lifecycle::onStart ---> this = $this")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        xlog("BaseActivity::Lifecycle::onSaveInstanceState ---> this = $this, window=$window")
    }

    override fun onResume() {
        super.onResume()
        xlog("BaseActivity::Lifecycle::onResume ---> this = $this")
    }

    override fun onPause() {
        super.onPause()
        xlog("BaseActivity::Lifecycle::onPause ---> this = $this")
    }

    override fun onStop() {
        super.onStop()
        xlog("BaseActivity::Lifecycle::onStop ---> this = $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        xlog("BaseActivity::Lifecycle::onDestroy ---> this = $this")
    }

}