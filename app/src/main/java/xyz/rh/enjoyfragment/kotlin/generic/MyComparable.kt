package xyz.rh.enjoyfragment.kotlin.generic

import androidx.collection.ArrayMap
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

/**
 * Created by rayxiong on 2022/11/27.
 */
interface MyComparable<T> {

    fun compareTo(other: T): Int

}


fun main() {

    val bidsMapString = "{\"car1\": 123, \"car2\": 234, \"car3\": 456, \"car4\": 789}"
    val bidsMap = JSON.parseObject(bidsMapString, object : TypeReference<HashMap<String, Int>>(){})
    println("bidsMap.tostring == " + bidsMap.toString())


    val stringBuilder = StringBuilder()
    stringBuilder.append("{")
//    for (entry in bidsMap.entries) {
//        if (entry == null || TextUtils.isEmpty(entry.key)) continue
//        stringBuilder.append("\"")
//            .append(entry.key)
//            .append("\"")
//            .append(":")
//            .append(entry.value)
//            .append(",")
//    }

//    bidsMap.entries.forEach {
//
//        stringBuilder.append("\"")
//            .append(it.key)
//            .append("\"")
//            .append(":")
//            .append(it.value)
//            .append(",")
//    }
//    stringBuilder.deleteAt(stringBuilder.lastIndexOf(","))
//    stringBuilder.append("}")
//
//    println("========== stringBuilder ===  $stringBuilder")

//    var showType: String? = ""
//    if (showType?.isEmpty() == true) {
//        println("111111")
//    } else {
//        println("222222")
//    }


    // 关于ArrayMap报错记录：Stub! 错误
    // 原因是我项目使用了androidx，如果使用了android.util.ArrayMap就会报错，必须使用androidx下的ArrayMap
    // android.util.ArrayMap
    // androidx.collection.ArrayMap
//    Exception in thread "main" java.lang.RuntimeException: Stub!
//    at android.util.ArrayMap.<init>(ArrayMap.java:25)
//    at xyz.rh.enjoyframent.kotlin.generic.MyComparableKt.main(MyComparable.kt:58)
//    at xyz.rh.enjoyframent.kotlin.generic.MyComparableKt.main(MyComparable.kt)


    var arrayMap = ArrayMap<String, String>(10)
    arrayMap.put("show_type", "abc")
    arrayMap.put("show_type", "xyz")
    println("map = $arrayMap")



}