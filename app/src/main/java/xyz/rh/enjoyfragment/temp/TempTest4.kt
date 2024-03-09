package xyz.rh.enjoyfragment.temp

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by rayxiong on 2023/12/20.
 */
class TempTest4 {




}

fun main() {



    println(123)

    val datalist = listOf<String>("1", "2")

    datalist.runIfNotNullOrEmpty {
        println("block runIfNotNullOrEmpty ====")
//        null
//        Any()
        returnNullFun()
    }
        ?: run {
        println("this is  run  block -------")
    }



    val eventsFlow = MutableSharedFlow<String>() // 这里以字符串作为事件的例子
    val job = eventsFlow
        .debounce(200) // 200毫秒的防抖时间
        .onEach { event ->
            // 处理最新的事件
            doSomeThing(event)
        }
        .launchIn(CoroutineScope(Dispatchers.Default)) // 在合适的协程作用域中启动


    var count = 1
    runBlocking {
//        GlobalScope.launch {
            repeat(1000) {
                flow<String> {
                    eventsFlow.emit("data ${count++}")
                }
            }
//        }
    }


}

fun doSomeThing(event: String) {
    println("doSomeThing:: event=$event")
}

fun returnNullFun() : Any? {
    return null
}


inline fun <T, R> List<T>?.runIfNotNullOrEmpty(block: List<T>.() -> R): R? {
    return if (this != null && size > 0) block() else null
}

