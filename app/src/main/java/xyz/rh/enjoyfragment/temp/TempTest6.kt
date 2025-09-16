package xyz.rh.enjoyfragment.temp

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import xyz.rh.common.xlog
import xyz.rh.common.xprintln
import java.util.LinkedList
import kotlin.contracts.contract


/**
 * Created by rayxiong on 2025/2/19.
 */

fun main() {
    println(123)
//    val str: String? = null
//    str.equals("abc")
//    println("end")


//    val list = LinkedList<String>()
//    list.add("a")
//    list.add("b")
//    list.add("c")
//    list.add("c")
//    list.add("c")
//    list.add("d")

//    while (list.peek() != null) {
//        println("打印：${list.poll()}")
//    }
//    val r = "null".split("?")[0]
//    println(r == null)
//    println(r)

//    val map: Map<String, Any>? = null
//    map.isNotEmpty()


    runBlocking {

        xprintln("ray::testmainscope =======1")
        GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            xprintln("ray::testmainscope ==== in globalScope ===2")
            delay(5000)
            xprintln("ray::testmainscope ==== in globalScope, 5秒后 ===3")
        }
        xprintln("ray::testmainscope =======4")

        xprintln("runBlocking ---END---")
    }




}
