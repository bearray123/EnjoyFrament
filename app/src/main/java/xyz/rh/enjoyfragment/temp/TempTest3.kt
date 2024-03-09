package xyz.rh.enjoyfragment.temp

import xyz.rh.common.log
import xyz.rh.enjoyfragment.javacallkt.MyJavaClass

/**
 * Created by rayxiong on 2023/9/3.
 */


fun main() {



    var pendingJumpNodeList = ArrayList<String>(5)
    pendingJumpNodeList.add("11111sss")
    pendingJumpNodeList.add("222222")
    pendingJumpNodeList.add("3333333sss")
    pendingJumpNodeList.add("44444444sss")
    pendingJumpNodeList.add("555555555sss")

    pendingJumpNodeList.add("6666666666")

    val find = pendingJumpNodeList.findLast {
        it.contains("sss")
    }
    println("find last string == $find")


    val cfn1 = Class.forName("xyz.rh.enjoyframent.temp.TestGson")

    println("cfn1 =? Class<*> ${cfn1 is Class<*>}")
    println("cfn1 =? Any ${cfn1 is Any}")
    println("cfn1 =? Object ${cfn1 is Object}")

    val list1 = listOf(1,2,3,4,5,6,7,8,9)

    var count = 0
    list1.forEach mylabel@ {
        if (count == 3) {
//            return // 直接return main，不会打印 the END
//            return@forEach // 和自己额外添加标签mylabel功能相同，表示跳过本次遍历，相当于continue功能
//            count++
            println("count == 3")
            return@mylabel
        }
        println("----$it")
        count++
    }

    println("the END")


    val getNameFromJava  = MyJavaClass.getInstance()?.name
    println("getNameFromJava == $getNameFromJava")


    val map1 = mutableMapOf<String, Int>(
            "123" to 123,
            "456" to 456
    )

//    map1.also {
//        "123" to 123
//        "456" to 456
//    }
    println("map1.size = ${map1.size}")



}