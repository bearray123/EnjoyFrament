package xyz.rh.enjoyfragment.kotlin.lambda

/**
 * Created by rayxiong on 2022/11/11.
 */
class Poo {

    fun testa(callback: (Int) -> String) {
        println("test a start!")
//        callback.invoke(123)
        testb(callback)
    }



    fun testb(callback: (Int) -> String) {
        println("test b start!")
//        callback.invoke(123)
        callback(123)
        callback.invoke(456)
    }

    fun testc(callback: DefaultCallback) {
        callback.onResult("")
    }

    fun delay(time: Long, block: (p1: Long) -> String) {
        println("test delay start!")
        Thread.sleep(time)
        block(time)
    }

}

interface DefaultCallback {
    fun onResult(data: String)
}


fun main() {

    val poo = Poo()
//    poo.testa() { p1 ->
//
//    }

//    poo.testa {
//        println("调用时传的闭包：$it")
//        "这是从传进去的闭包返回的字符串"
//    }

    poo.delay(5000) {
        println("过了 $it, 执行了")
        "返回结果是string"
    }

    poo.testa { intParam ->
        println()
        ""
    }

//    poo.testa(object : )

    poo.testc(object : DefaultCallback {
        override fun onResult(data: String) {
            println(data)
        }
    })

//    poo.testc {
//
//    }

}