package xyz.rh.enjoyfragment.temp

/**
 * Created by rayxiong on 2025/8/7.
 */


fun main() {

    
    var fun1 : (str: Int) -> Boolean = { num ->
        true
    }

    var fun2 :  (str: Int) -> Boolean = { num ->
        true
    }

    println(fun1.javaClass === fun2.javaClass)

}