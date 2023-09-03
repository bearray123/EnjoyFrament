package xyz.rh.enjoyframent.temp

/**
 * Created by rayxiong on 2023/9/3.
 */


fun main() {

    println(" test foreach return")


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

}