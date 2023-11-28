package xyz.rh.enjoyfragment

import android.app.Application
import android.content.Context
import xyz.rh.common.log

/**
 * Created by rayxiong on 2023/9/12.
 */
class EnjoyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        log("EnjoyApplication:: onCreate()")


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        log("EnjoyApplication:: attachBaseContext()")

        log("EnjoyApplication:: attachBaseContext --> app = $this, app.applicationContext = ${this.applicationContext}")


    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }



}