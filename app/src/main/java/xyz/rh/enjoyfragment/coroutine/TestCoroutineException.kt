package xyz.rh.enjoyfragment.coroutine

import kotlinx.coroutines.*
import xyz.rh.common.xprintln
import java.lang.RuntimeException
import kotlin.concurrent.thread
import kotlin.coroutines.resumeWithException

/**
 * Created by rayxiong on 2024/5/8.
 */


fun main() = runBlocking {
    xprintln("start")
    launch(Dispatchers.IO) {
        xprintln(1)
        delay(2000)
        xprintln(2)
    }

    xprintln(3)


    val ret =
        try {
            doNetwork()
        } catch (e: Exception) {
            "exce"
        }

    xprintln("ret = $ret")

}

suspend fun doNetwork() : String? {
    var result: String? = null
    withContext(Dispatchers.IO) {
        try {
            result = suspendCancellableCoroutine { cont ->
                try {
                    // 这里调用抛了运行时异常，可以try catch住，但在Catch里继续resumeWithException
                    realDo {
                        cont.resume(it,null)
                    }
                } catch (e: Exception) {
                    xprintln("try catch !!! e=$e")
                    cont.resumeWithException(e)
                }
            }
        } catch (e: Exception) {
            xprintln("try catch !!!!!!!! e=$e")
        }
    }
    return result
}

fun realDo(callback: (result: String) -> Unit) {
    val s: String? = null
    s!!.length
    thread {
        var count = 0
        while (count <=5) {
            xprintln("realDo == ${count++}")
            Thread.sleep(1000)

//            if (count == 3) {
//                throw RuntimeException("my exception!")
//            }
        }
        callback.invoke("this is net work data!")
    }
}