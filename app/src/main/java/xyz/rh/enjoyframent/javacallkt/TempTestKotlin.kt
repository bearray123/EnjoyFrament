package xyz.rh.enjoyframent.javacallkt

/**
 * Created by rayxiong on 2022/12/8.
 */
class TempTestKotlin {


}

fun main() {

    println("hello java call to kotlin")

    val callback = object :
        TestCallbackWithNullParam {
        override fun testfun(name: String, price: Int) {
            // 结论是 在参数传入处就已经跑了NPE，跟真正是否使用这个空参数无关
//            xlog(name.length)
        }

    }

    val java = JavaCallK()
    java.callback = callback

    var p1: String? = ""
    p1 = null

    // 测试java调用到kt，主要看在kt回调处如果是非空类型，如果在java调用时传的是空，那么抛异常到底是在传入参数处，还是在真正使用这个空参数时才发生NPE
    // 结论是 在参数传入处就已经跑了NPE，跟真正是否使用这个空参数无关
    (java.callback as TestCallbackWithNullParam).testfun(p1!!, 123)


}