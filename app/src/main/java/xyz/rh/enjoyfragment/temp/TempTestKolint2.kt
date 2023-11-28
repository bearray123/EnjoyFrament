package xyz.rh.enjoyfragment.temp

import xyz.rh.common.xprintln

/**
 * Created by rayxiong on 2023/8/18.
 */


fun main() {

//    xlog("hello temp hello2")  // 纯kotlin不带Android环境的不能直接用log，会报Stub! 桩代码异常

//    Exception in thread "main" java.lang.RuntimeException: Stub!
//    at android.util.Log.d(Log.java:29)
//    at xyz.rh.common.LogExtentionKt.xlog(LogExtention.kt:15)
//    at xyz.rh.common.LogExtentionKt.xlog(LogExtention.kt:20)
//    at xyz.rh.enjoyframent.temp.TempTestKolint2Kt.main(TempTestKolint2.kt:12)
//    at xyz.rh.enjoyframent.temp.TempTestKolint2Kt.main(TempTestKolint2.kt)

    xprintln("hello temp hello2")

    var firstValue : Int? = null

    firstValue = 1

    val result = (firstValue ?: 100) - 50

    xprintln("result = $result")

    // 在java中 子线程出现运行时异常 不会影响main线程的执行，但在Android中是会影响到app停止运行的，因为Android中通过全局的exceptionCaughthandler捕获异常，当任何线程出现未捕获的异常后都会将app停止运行
    // 异常无法通过跨线程进行捕获，比如在main线程中是无法捕获到子线程内部的异常的


    val map = HashMap<String, Any?>()

    map["version"] = "6.2.7"

    map["code"] = 1000

    val ret = map.remove("a3_token")

    val ret2 = map.remove("code")


    xprintln("end --- ret=$ret, ret2=$ret2,  map.size = ${map.size}")

    A3TokenManager.geta3token()


}

object A3TokenManager {



    @JvmStatic
    fun geta3token() : String {
        return ""
    }

}
