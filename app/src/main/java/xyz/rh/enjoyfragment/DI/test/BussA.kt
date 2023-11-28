package xyz.rh.enjoyfragment.di.test

import javax.inject.Inject


/**
 * Created by xionglei01@baidu.com on 2022/10/2.
 */
class BussA @Inject constructor(){ // BussA为自己工程里的类，可直接在构造器上使用@Inject注解来生成对象


    fun method1A() {
        println("methond1A  execute...")
    }

    fun method2A() {
        println("methond2A  execute...")
    }

    fun method3A() {
        println("methond3A  execute...")
    }

}