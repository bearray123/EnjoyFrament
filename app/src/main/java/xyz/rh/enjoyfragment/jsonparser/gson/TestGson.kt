package xyz.rh.enjoyfragment.jsonparser.gson

import com.google.gson.*
import com.google.gson.annotations.SerializedName

/**
 * Created by rayxiong on 2023/10/15.
 */

fun main() {

    println("test GSON")


//    val gson1 = "a"
//
//    var gson : Student? = null
//    val result = runCatching {
//        gson = Gson().fromJson(gson1, Student::class.java)
//    }
//
//    println("result = $result")
//    println("gson = $gson")

    val gson = Gson()
    val myGson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER) // 数字类型解析策略，解析后类型为LazilyParsedNumber（Gson 2.9.0）
        .registerTypeAdapterFactory(ParseErrorSkipGsonTypeAdapterFactory()) // 解决因某个字段解析报错（如类型不匹配）时导致整个解析都失败的问题
        .create()
    val parserResult1 = myGson.fromJson(modelStr2, Model1::class.java)


    println("parserResult1.age = ${parserResult1.age}")
    println("parserResult1 = $parserResult1")


}

//
data class Model1(

    @SerializedName("name")
    var name: String = "defaultName",

    @SerializedName("age")
    var age: Int = -1, //Kotlin Int类型，不加？会转换成java的int类型，加？会转换成Integer类型；Boolean, Long等类型类似

    @SerializedName("data")
    var data: JsonElement?,

    // 验证GSon在低版本里的缺陷：如果定义map类型，里面的value如果下发是int，long时，会默认解析成double类型
    // 只要是非基础类型的引用类型都需要定义成可空类型Map<String, Any>?，否则服务端下发有问题就会出现NPE
    @SerializedName("map_data")
    var mapData: Map<String, Any>,

    // 其实被解析成double跟Map本身无关，只是在定义Any时在gson解析Number过程中是按照默认double来的
    // 验证GSon在低版本里的缺陷：json下发是int时被解析成double类型
    @SerializedName("age_any")
    var ageAny: Any

)

val modelStr1 = """
        {
            "name": "xionglei-这里data是JsonObject",
            "age": 30,
            "data": {
                "k1": "v1-str",
                "k2": 2,
                "k3": true
            }
        }
    """
val modelStr2 = """
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
            }
            ],
            "map_data": {
                "map_age" : 100,
                "map_height" : 190
            },
            "age_any" : 100
        }
    """
val modelStr3 = """
        {
            "name": "xionglei-这里data是基础类型",
            "age": 30,
            "data": 12.5
        }
    """
