package xyz.rh.enjoyframent.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.rh.enjoyframent.R

/**
 *
 * DeepLink启动方式：adb shell am start -a android.intent.action.VIEW -d "rayhung://dodohost"
 * 注意：scheme://host  scheme是区分大小写的！！！host不用区分大小写！！！可以在manifest里配置path
 *
 * Created by rayxiong on 2023/5/5.
 */
class DeepLinkActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.deeplink_activity_layout)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}