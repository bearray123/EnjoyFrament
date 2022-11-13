package xyz.rh.common

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

/**
 * Created by rayxiong on 2022/10/30.
 */

fun xlog(tag : String? = null, msg : Any?) {
    Log.d(tag, "[t_name=${Thread.currentThread().name}]: $msg")
}

@RequiresApi(Build.VERSION_CODES.O)
fun xprintln(msg: Any?) {
    println("${LocalDateTime.now()} : [${Thread.currentThread().name}]: $msg")
}
