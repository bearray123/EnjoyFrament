package xyz.rh.enjoyfragment.jsonparser.gson

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * 测试数据model里用List如果泛型参数定义的是非空类型，但服务端如果下发数组里存在null, 例如[1,3,null]  Gson自动解析后结果到底是啥？
 * 结论：解析出来的是null
 * Created by rayxiong on 2024/8/8.
 */

fun main() {

    val gson = GsonBuilder().create()
    val model = gson.fromJson(modelStr, ModelWithArray::class.java)
    println("model.data.size = ${model.data?.size}")
    println("model = $model")
    model.data?.forEach { item ->
        println("item = $item, k1=${item.k1}") // 崩溃，由于data定义的是data: MutableList<Data>? 这里item是非空，但gson自动解析出来是null

    }

}

data class ModelWithArray(
    // 在list里申明非空的类型，看服务端如果返回数组里存在null，端上会异常吗？结论：会抛运行时异常，崩溃！
    // 所以后续我们在定义model里的list时，泛型参数的类型最好定义成非空类型！
    @SerializedName("data")
    var data: MutableList<Data>?,
)

data class Data(
    @SerializedName("k1")
    val k1: String?,
    @SerializedName("k2")
    val k2: Int,
    @SerializedName("k3")
    val k3: Boolean,
)


const val modelStr = """
        {
            "name": "xionglei-这里data是JsonArray",
            "age": null,
            "data": [{
                "a1": "v1-str",
                "a2": 2,
                "a3": true
            },
            {
                "a1": "v1-str",
                "a2": 2,
                "a3": true
            },
            {
                "a1": "v1-str",
                "a2": 2,
                "a3": true
            },
            null
            ],
            "map_data": {
                "map_age" : 100,
                "map_height" : 190
            },
            "age_any" : 100
        }
    """