package xyz.rh.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.CopyOnWriteArrayList
import java.util.regex.Pattern

/**
 * Created by rayxiong on 2023/1/18.
 */

private val VERSION_NAME_PATTERN: Pattern? = Pattern.compile("(\\d+\\.\\d+\\.\\d+)\\-*.*")


@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    println(123)

    val matcher = VERSION_NAME_PATTERN?.matcher("6.5.14.90-123456")
    println(matcher?.group(1))




    val map = mutableMapOf<String, Int>(
        "k1" to 1,
        "k2" to 2,
        "k3" to 3
    )

//    val map2: ConcurrentHashMap<String, Int> = ConcurrentHashMap()
    val map2: CopyOnWriteArrayList<Int> = CopyOnWriteArrayList()
    val list1 = mutableListOf<Int>()
//    val list1 = CopyOnWriteArrayList<Int>()
    list1.add(1)
    list1.add(2)
    list1.add(3)

//    map2["k1"] = 1
//    map2["k2"] = 2
//    map2["k3"] = 3

//    thread {
//        Thread.sleep(100)
//        map["K4"] = 4
////        list1.add(4)
//        xprintln("k4 添加成功")
//    }

//    map.forEach { entry ->
//        xprintln("entry : ${entry.key} : ${entry.value} ")
//        Thread.sleep(500)
//    }

    val interator = map2.iterator()
//    interator.forEach { entry ->
//        xprintln("entry : ${entry.key} : ${entry.value} ")
//        Thread.sleep(500)
//    }
//    while (interator.hasNext()) {
//        val entry = interator.next()
//        xprintln("entry : ${entry.key} : ${entry.value} ")
//        Thread.sleep(500)
//    }

//    list1.forEach {
//        xprintln("entry : $it")
//        Thread.sleep(500)
//    }

//    map.forEach { entry ->
//        xprintln("entry : ${entry.key} : ${entry.value} ")
//        Thread.sleep(500)
//    }
//
//    val hh = Object()
//    hh.wait()
//    hh.clone


}
