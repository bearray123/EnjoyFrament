package xyz.rh.common

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

/**
 * Created by rayxiong on 2022/10/30.
 */

@Keep
fun xlog(tag : String? = null, msg : Any?) {
    Log.d(tag, "[t_name=${Thread.currentThread().name}]: $msg")
}

@Keep
fun xlog(msg : Any?) {
    xlog("", msg)
}

@SuppressLint("NewApi")
@Keep
fun xprintln(msg: Any?) {
    println("${LocalDateTime.now()} : [${Thread.currentThread().name}]: $msg")

}
