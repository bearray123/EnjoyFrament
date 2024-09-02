package xyz.rh.enjoyfragment.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by rayxiong on 2024/9/2.
 */

fun main() {

    // 调度器Unconfined
    // Dispatchers.Unconfined的含义是不给协程指定运行的线程，在第一次被挂起(suspend)之前，由启动协程的线程执行它，
    // 但被挂起后, 会由恢复协程的线程继续执行, 如果一个协程会被挂起多次, 那么每次被恢复后, 都有可能被不同线程继续执行
    GlobalScope.launch(Dispatchers.Unconfined){
        println(Thread.currentThread().name)

        // 挂起
        withContext(Dispatchers.IO){
            println(Thread.currentThread().name)
        }
        // 恢复
        println(Thread.currentThread().name)

        // 挂起
        withContext(Dispatchers.Default){
            println(Thread.currentThread().name)
        }
        // 恢复
        println(Thread.currentThread().name)
    }

    //进程保活
    Thread.sleep(1000)

}