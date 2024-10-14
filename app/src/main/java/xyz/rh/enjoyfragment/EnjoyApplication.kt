package xyz.rh.enjoyfragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import xyz.rh.common.log

/**
 * Created by rayxiong on 2023/9/12.
 */
class EnjoyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        log("EnjoyApplication:: onCreate()")

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                log("ActivityTrack:onActivityCreated activity:$activity")
            }

            override fun onActivityStarted(activity: Activity) {
                log("ActivityTrack:onActivityStarted activity:$activity")
            }

            override fun onActivityResumed(activity: Activity) {
                log("ActivityTrack:onActivityResumed activity:$activity")
            }

            override fun onActivityPaused(activity: Activity) {
                log("ActivityTrack:onActivityPaused activity:$activity")

            }

            override fun onActivityStopped(activity: Activity) {
                log("ActivityTrack:onActivityStopped activity:$activity")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                log("ActivityTrack:onActivityDestroyed activity:$activity")
            }

        })


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        log("EnjoyApplication:: attachBaseContext()")

        log("EnjoyApplication:: attachBaseContext --> app = $this, app.applicationContext = ${this.applicationContext}")

        Looper.getMainLooper().setMessageLogging { log ->
//            Log.d("","log message => $log")
        }


    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }



}