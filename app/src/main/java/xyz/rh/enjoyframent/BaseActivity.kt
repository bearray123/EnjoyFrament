package xyz.rh.enjoyframent

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
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


        val contentView = findViewById<View>(R.id.content) // FrameLayout
        // DecorView extends FrameLayout
        // 对Activity、Window、DecorView的总结：
        //
        xlog("BaseActivity::Lifecycle::onCreate --->[ this = $this ], [window=$window], [window.decorView = ${window.decorView}] , findViewById(R.id.content) = $contentView")


    }


    override fun onStart() {
        super.onStart()
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
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        xlog("BaseActivity::Lifecycle::onDestroy ---> this = $this")
    }



}