package xyz.rh.enjoyfragment.temp

import com.google.gson.Gson
import xyz.rh.enjoyfragment.jsonparser.Student

/**
 * Created by rayxiong on 2023/10/15.
 */
class TestGson {
}


fun main() {

    println("test GSON")


    val gson1 = "a"

    var gson : Student? = null
    val result = runCatching {
        gson = Gson().fromJson(gson1, Student::class.java)
    }

    println("result = $result")
    println("gson = $gson")


}