package xyz.rh.enjoyfragment.temp

import kotlinx.coroutines.*
import xyz.rh.common.xprintln
import kotlin.concurrent.thread
import kotlin.coroutines.resume

/**
 * Created by rayxiong on 2024/4/26.
 */


fun main() {

    val context = Job() + Dispatchers.IO + CoroutineExceptionHandler { context, throwable ->
        xprintln("${context[CoroutineName]} 发生了异常: $throwable")
    }

    val myCoroutineScope = CoroutineScope(context)

    runBlocking {

        xprintln("################## runBlocking  start ##################  ")

        myCoroutineScope.launch {
            xprintln("myCoroutineScope  launch:::::::")
            val result = getData()
            xprintln("myCoroutineScope  getData == $result，协程有返回，获取到了数据")
        }

//        delay(2000)
//        myCoroutineScope.cancel()

        xprintln("myCoroutineScope  cancel()")

        // 控制main函数最多执行多长时间
        delay(50_000)

        xprintln("##################  runBlocking  end!  ##################")

    }


}

suspend fun getData(): String {
    return suspendCancellableCoroutine<String> { continuation ->

        xprintln("suspendCancellableCoroutine start....")
        val workThread = getRealDataOnSubThread {
            xprintln("callback invoke...")
            continuation.resume(it)
        }

        // 协程取消的监听
        continuation.invokeOnCancellation { throwable ->
            // 这里回调所在的线程跟调用cancel()所在的线程
            xprintln("suspendCancellableCoroutine on Cancel callback 被取消了")
            workThread.interrupt()
        }

        xprintln("suspendCancellableCoroutine end!")

//        continuation.resume("这个是耗时操作后得到的数据", null)
    }
}


fun getRealDataOnSubThread(callBack: (result: String) -> Unit): Thread {
    return thread(name = "get-real-data-thread") {
        var counts = 0
        while (counts < 10) {

//            val aaa = runCatching {
//                Thread.sleep(1000)
//                xprintln("getting data...过了 ${++counts} 秒!")
//                callBack.invoke("任务线程被打断了，停止运行！")
//                123
//            }
            try {
                Thread.sleep(1000)
                xprintln("getting data...过了 ${++counts} 秒!")
            } catch (exception : InterruptedException) {
                exception.printStackTrace()
                callBack.invoke("任务线程被打断了，停止运行！")
                return@thread
            }
        }
        callBack.invoke("任务线程正常运行完成，这是通过子线程获取到的real data")
    }
}