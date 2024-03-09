package xyz.rh.enjoyfragment.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Created by rayxiong on 2024/1/15.
 */


fun main() {

    runBlocking {
        val flow1: Flow<Int> = flow {
            emit(0)
            emit(1)
            emit(2)
            emit(3)
            emit(4)
        }
        flow1.filter {      //中转站①
            it > 2
        }.map {         //中转站②
            it * 2
        }.collect {     //接收
            println("it：$it")
        }
    }

}