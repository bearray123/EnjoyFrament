package xyz.rh.enjoyframent.jsonparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.BaseActivity

/**
 * Created by rayxiong on 2023/5/5.
 */
class TestJsonParserActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这里不setContentView(), 所以直接是一个空白页面


    }

    override fun onResume() {
        super.onResume()

        val s1 =  Gson().fromJson(data, Student::class.java)
        xlog("after from json::: s1 = $s1")

//        val s2 =  Gson().fromJson(data, Student2::class.java)
//        xlog("after from json::: s2 = $s2")
//
//        val s1_hm = s1.hobbyMap
//        val valueOfH1 = s1_hm["h1"]
//        xlog("valueOfH1==== newdata = ${valueOfH1.toString()}")
//
//        val s3 = Gson().fromJson(valueOfH1.toString(), Hobby::class.java)
//        xlog("after from json::: s3 = $s3")

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}

const val data = """
    {
        "name" : "dodo",
        "age": "18",
        "hobby_map": {
            "h1": {
                "name": "看动画片",
                "level": 0
            },
            "h2": {
                "name": "switch",
                "level": 1
            },
            "h3": {
                "name": "eatting",
                "level": 3
            },
            "h4": 100
        }
    }
"""

data class Student(
    @SerializedName("name")
    var name: String,
    @SerializedName("age")
    var age: Int? = 999,
    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Any>)


data class Student2(
    @SerializedName("name")
    var name: String,
    @SerializedName("age")
    var age: Int,
    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Any>)

data class Hobby(
    @SerializedName("name")
    var name: String,
    @SerializedName("level")
    var level: Int
    )