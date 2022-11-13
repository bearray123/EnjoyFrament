package xyz.rh.enjoyframent.kotlin

/**
 * Created by rayxiong on 2022/11/12.
 */
class Something<T> : Cloneable {


    fun work(): T? {
        val str = ""
        return str as? T
    }


}