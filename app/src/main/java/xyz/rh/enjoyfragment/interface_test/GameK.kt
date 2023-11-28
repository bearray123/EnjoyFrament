package xyz.rh.enjoyfragment.interface_test

/**
 * Created by xionglei01@baidu.com on 2022/10/15.
 */
open class GameK() {

    inline fun <reified A>  build(): A? {
        val a = object : ABC {
            override fun play() {
                TODO("Not yet implemented")
            }

        }
//        a is A
        return if (a is A) {
            a
        } else null
    }


    protected fun testABC() {

    }

}