package xyz.rh.enjoyfragment.coroutine

import kotlinx.coroutines.*

/**
 * Created by rayxiong on 2024/5/6.
 */

fun main() {


    GlobalScope.launch {
        println("in GlobalScope, before loadData")
        loadData()
        println("in GlobalScope, after loadData!")
    }

}

suspend fun loadData() {
    return withContext(Dispatchers.IO) {
        println("in withContext block, start")
        delay(3000)
        println("in withContext block, end!")
        123
    }
}
