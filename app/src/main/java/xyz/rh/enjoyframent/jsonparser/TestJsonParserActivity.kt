package xyz.rh.enjoyframent.jsonparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.BaseActivity
import xyz.rh.enjoyframent.jsonparser.gson.MyJSONParserUtils

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

//        val s1 =  Gson().fromJson(data, Student2::class.java)
//        xlog("after from json::: s1 = $s1")

//        val s2 =  Gson().fromJson(data, Student2::class.java)
//        xlog("after from json::: s2 = $s2")
//
//        val s1_hm = s1.hobbyMap
//        val valueOfH1 = s1_hm["h1"]
//        xlog("valueOfH1==== newdata = ${valueOfH1.toString()}")
//
//        val s3 = Gson().fromJson(valueOfH1.toString(), Hobby::class.java)
//        xlog("after from json::: s3 = $s3")


        val student2 = MyJSONParserUtils.convertJsonFromClass(data2, Student::class.java)
        println("MyJSONParserUtils.convertJsonFromClass ---->  student2=$student2")

        val student3 = MyJSONParserUtils.convertJsonFromClass(data3, Student::class.java)
        println("MyJSONParserUtils.convertJsonFromClass ---->  student3=$student3")

        val teacher = MyJSONParserUtils.convertJsonFromClass(data3, Teacher::class.java)
        println("MyJSONParserUtils.convertJsonFromClass ---->  teacher=$teacher")


        // 验证Gson自定义TypeAdapterFactory来兼容服务端下发类型不匹配导致整个model解析报错崩溃的问题
        // 特意传入错误类型的json，例如把定义成string类型的name传入 true,[],{}等 用原生Gson解析就报错崩溃
        // 用ParseErrorSkipGsonTypeAdapterFactory兼容的话就跳过name，不影响其他字段的解析，最终name解析出来为null
        val studentError = MyJSONParserUtils.convertJsonFromClass(dataError, Student::class.java)
        println("MyJSONParserUtils.convertJsonFromClass ---->  studentError=$studentError")




        val mapWrapListStudent = MyJSONParserUtils.convertJsonFromClass(data4, ComplexStudent::class.java)
        println("MyJSONParserUtils.convertJsonFromClass ---->  mapWrapListStudent=$mapWrapListStudent")




        // 用原始未加工的Gson解析如果字段出现类型不匹配就会导致整个json解析失败，出现崩溃
        // Student中name定义的是String
        // 如果name返回是空的JSONObject {} ---> Caused by: java.lang.IllegalStateException: Expected a string but was BEGIN_OBJECT
        // 如果name返回是空数组 [] ---> Caused by: java.lang.IllegalStateException: Expected a string but was BEGIN_ARRAY
        // 如果name返回是bool类型 true ---> Caused by: java.lang.IllegalStateException: Expected an int but was BEGIN_OBJECT
//        val studentError2 = Gson().fromJson(dataError, Student::class.java)
//        println("MyJSONParserUtils.convertJsonFromClass ---->  studentError=$studentError")
//        println("Gson().fromJson ---->  studentError2=$studentError2")



        val typeTokenModel: List<Map<String, Hobby>>? = MyJSONParserUtils.convertJsonFromTypeToken(typeTokenData, object: TypeToken<List<Map<String, Hobby>>>(){}.type)
        println("MyJSONParserUtils.convertJsonFromTypeToken ---->  typeTokenData=$typeTokenModel")

//        val typeTokenModel2: List<Map<String, Hobby>>? = Gson().fromJson(typeTokenData, object: TypeToken<List<Map<String, Hobby>>>(){}.type)
//        println("MyJSONParserUtils.convertJsonFromTypeToken ---->  typeTokenData=$typeTokenModel2")

        val typeTokenModel2: List<Hobby>? = Gson().fromJson(typeTokenData2, object: TypeToken<List<Hobby>>(){}.type)
        println("MyJSONParserUtils.convertJsonFromTypeToken ---->  typeTokenModel2=$typeTokenModel2")



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


// List<Map<String, Hobby>>外层是一个数组，里面包含的内容是map组成的；需要用typetoken解析
const val typeTokenData = """
    [
        {   "hobby_map1": {
                "h1": {
                    "name": "看动画片",
                    "level": 0
                },
                "h2": {
                    "name": "switch",
                    "level": 1
                }
            }
        },
        {   "hobby_map2": {
                "h21": {
                    "name": "2看动画片",
                    "level": 0
                },
                "h22": {
                    "name": "2switch",
                    "level": 21
                }
            }
        }
    ]
"""

// List<Hobby>外层是一个数组，里面包含Hobby；需要用typetoken解析
const val typeTokenData2 = """
    [
        {
           "name": "看动画片",
           "level": 0,
           "innermap" : {
                "mapk1" : 100,
                "mapk2" : 200
           }
        },
        {
           "name": "switch",
           "level": 1,
           "innermap" : {
                "mapk3" : 300,
                "mapk4" : 400
           }
        }
    ]
"""

// 特意将 name 返回为错误的类型：例如 true， []， {} 等，用原始Gson.from就会报错，崩溃
const val dataError = """
    {
        "name" : [],
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

// 少了age的返回
const val data2 = """
    {
        "name" : "dodo",
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

// 少了name，少了age：解析后name不会走默认的，而是null；name也不会走默认的-1，而是0
const val data3 = """
    {
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

// 额外加了map嵌套list的复杂类型
const val data4 = """
    {
        "name" : "dodo",
        "age" : 18,
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
        },
        "lovers" : {
            "damimi" : [80,66,90],
            "avnv" : [10,20,30]
        }
    }
"""

data class Student(

    // 现象及结论：当服务端不返回name时，这里虽然定义了构造器里默认传defaultName，但是由于gson通过反射无参构造器是无效的（因为它本身没有无参构造器），
    // 最终会走Unsafe的不安全方式绕过构造器去生成对象，这样属性其实是都没有赋值的，理论上都是反编译java之后默认的值，即string是null，Int是0，Map是null
    @SerializedName("name")
    var name: String = "defaultName",

    @SerializedName("age")
    var age: Int = -1,

    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Int>
    )



data class Student2(
    @SerializedName("name")
    var name: String,
    @SerializedName("age")
    var age: Int,
    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Any>)


// 可以通过向构造函数添加一个 @JvmOverloads 注解来解决，这种方式实际上也是通过提供一个无参构造函数来解决问题的。
// 所以缺点就是需要每个构造参数都提供默认值，所以才能生成无参构造函数
// 比如 如果hobbyMap 不给默认的空map，那么就不会生成无参构造器，最终name和age都不会走默认的值。
data class Teacher @JvmOverloads constructor(

    @SerializedName("name")
    var name: String = "defaultName",

    @SerializedName("age")
    var age: Int = -1,

    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Int> = mutableMapOf()
)

data class Hobby(
    @SerializedName("name")
    var sname: String,
    @SerializedName("level")
    var slevel: Int,
    @SerializedName("innermap")
    var sinnerMap: Map<String, Int>
    )

data class ComplexStudent(

    // 现象及结论：当服务端不返回name时，这里虽然定义了构造器里默认传defaultName，但是由于gson通过反射无参构造器是无效的（因为它本身没有无参构造器），
    // 最终会走Unsafe的不安全方式绕过构造器去生成对象，这样属性其实是都没有赋值的，理论上都是反编译java之后默认的值，即string是null，Int是0，Map是null
    @SerializedName("name")
    var name: String = "defaultName",

    @SerializedName("age")
    var age: Int = -1,

    @SerializedName("hobby_map")
    var hobbyMap: Map<String, Int>,

    // 这里额外加了一个map嵌套list的复杂结构
//    @SerializedName("lovers")
//    var lovers: HashMap<String, ArrayList<Int>?>?

)