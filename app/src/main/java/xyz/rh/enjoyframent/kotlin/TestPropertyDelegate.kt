package xyz.rh.enjoyframent.kotlin

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

    println("test property delegate")

    var main : TestPropertyDelegateMain?
    main = TestPropertyDelegateMain();
    main.intParam = "xionglei"


}