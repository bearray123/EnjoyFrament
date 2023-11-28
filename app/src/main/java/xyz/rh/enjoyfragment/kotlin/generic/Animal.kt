package xyz.rh.enjoyfragment.kotlin.generic

/**
 * 动物基类
 * Created by rayxiong on 2022/11/23.
 */
open class Animal: Biology{
    override fun isAlive() {
    }

    open fun shouting() {
        println("一个动物正在叫。。。")
    }

}