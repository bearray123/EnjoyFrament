package xyz.rh.enjoyfragment.temp

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by rayxiong on 2024/6/23.
 */
class TempTest5 {
}


class Delegate(var callback: String) {

    fun make(): String {
        return "callback invoke ${callback}"
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun main() {

    print("测试 ConcurrentHashMap")

    val map = ConcurrentHashMap<String, Delegate>()
    val delegate1 = Delegate("11111")
    val delegate3 = Delegate("33333")

    map.put("my_delegate", delegate1)
    map.put("my_delegate", delegate1)
    map.put("my_delegate", delegate3)
//    map.put("my_delegate", delegate1)

    print("map.size = ${map.size}")
    map.keys().asIterator().forEach {
        println("for each ===> ${map[it]?.make()}")
    }



}

