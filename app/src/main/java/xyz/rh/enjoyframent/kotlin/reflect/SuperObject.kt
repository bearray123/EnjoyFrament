package xyz.rh.enjoyframent.kotlin.reflect


/**
 * Created by rayxiong on 2022/11/22.
 */
open class SuperObject<T>: SuperSuperObject() {

    fun getObject(): T {
        val o = Object()
        return o as T
    }

}