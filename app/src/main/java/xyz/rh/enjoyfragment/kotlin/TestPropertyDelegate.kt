package xyz.rh.enjoyfragment.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by xionglei01@baidu.com on 2022/9/23.
 */
class TestPropertyDelegateMain {

//    var intParam: String by IntegerDelegate()
//    var intParam: String by Delegates.notNull()
        var intParam: String by Delegates.observable("dodo") { _, old, new ->
            println("the propertiy is changed, old=$old, new=$new")
        }

}


class IntegerDelegate<T> : ReadWriteProperty<TestPropertyDelegateMain, T> {

    var backingField: T? = null

    override operator fun getValue(gate: TestPropertyDelegateMain, p: KProperty<*>) : T{
        if (backingField == null) {
            throw IllegalStateException("Attempting to get value before it has been set.")
        }
        return backingField as T
    }

    override operator fun setValue(gate: TestPropertyDelegateMain, p: KProperty<*>, value: T) {
        if (backingField != null) {
            throw IllegalStateException("Attempting to set value after it has been set.")
        } else {
            backingField = value
        }
    }

}


fun main(args: Array<String>) {

//    println("test property delegate")
//
//    var main : TestPropertyDelegateMain?
//    main = TestPropertyDelegateMain();
//    main.intParam = "xionglei"

    val list = arrayOf(arrayOf(1,2,3), arrayOf(4,5,6,7), arrayOf(8,9,10,11,12))
//    var list2 = arrayListOf<String>("afj", "3", "276548", "dfjewiifcjicw", "90")
////    list.reverse()
//    val newlist = list.reversed()
//    list.forEach {
//        it.reverse()
//    }
//
//    val list3 = list2.filter {
//        it.length >= 3
//    }
//    val list4 = list2.map {
//        it.length
//    }
//
//    val list5 = list2.asSequence().filter {
//        it.length >= 3
//    }
//
//    println(list3)
//    println(list4)
////    println(list5)
//
//
//    var abc = SparseArray<String>()
//    abc.put(1, "acb")
//    abc.put(2, "cde")

//    run abc@{
//
//         list.forEach {
//
//            it.forEach xyz@{
//
//                if (it == 5) {
//                    return@abc
//                }
//
//                println(it)
//
//            }
//        }
//    }
    foree(list)



}

fun foree(list: Array<Array<Int>>) {
    list.forEach outer@{

        it.forEach inner@{

            if (it == 5) {
//                return@inner
                return@outer
            }
            println(it)

        }
    }
}