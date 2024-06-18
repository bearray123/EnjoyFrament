package xyz.rh.enjoyfragment.temp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.rh.common.xprintln
import kotlin.coroutines.CoroutineContext

/**
 * Created by rayxiong on 2024/4/27.
 */


fun main() = runBlocking<Unit> {
    xprintln("start")

    val scope1 = CoroutineScope(Job() + Dispatchers.IO) // 协同作用域
    val scope2 = CoroutineScope(SupervisorJob() + Dispatchers.IO) // 主从作用域

    val scope3 = CoroutineScope(SupervisorJob() + newSingleThreadContext("my-defined-thread")) // 主从作用域

    val channel1 = Channel<Int>(capacity = Channel.CONFLATED) { // 这个block的作用，an optional function that is called when element was sent but was not delivered to the consumer.
        xprintln("$it was not delivered to the consumer")
    }


    // 协程3用于发送数据
    scope3.launch {
        xprintln("123")
        for (i in 0..10) {
            channel1.send(i)
            delay(1000)
        }
    }

    // 协程2用于接受数据
    scope2.launch {
        val receiveData = channel1.receive()
        xprintln("receive: $receiveData")
    }

//    delay(20_000)
}


suspend fun getUser() {
    suspendCancellableCoroutine<String> {

    }
}
@JvmInline
value class Password(private val s: String)