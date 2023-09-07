package xyz.rh.enjoyframent.jsonparser.gson

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

object MyJSONParserUtils {
    var gson: Gson

    init {
        val builder = GsonBuilder()
        builder.registerTypeHierarchyAdapter(
            Map::class.java,
            CustomizedObjectTypeAdapter()
        )
        builder.registerTypeAdapterFactory(ParseErrorSkipGsonTypeAdapterFactory()).create()
        gson = builder.create()
    }

    /**
     * 通过class解析json
     */
    fun <T> convertJsonFromClass(json: String?, klass: Class<T>): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        return try {
            gson.fromJson(json, klass)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 通过Type解析json
     */
    fun <T> convertJsonFromTypeToken(json: String?, typeToken: Type): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        return try {
            gson.fromJson(json, typeToken)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    fun optString(json: JSONObject?, key: String?): String {
        return if (json == null || key.isNullOrEmpty()) {
            ""
        } else {
            json.optString(key, "")
        }
    }

    /**
     * 将对象转换为json
     */
    fun convertObject2Json(objects: Any?): String {
        return if (objects == null) {
            ""
        } else {
            try {
                gson.toJson(objects)
            } catch (e: Exception) {
                ""
            }
        }
    }

    /**
     * Object转gson-JSONObject
     */
    fun toGsonLibJsonObject(obj: Any?): JsonObject? {
        return try {
            val json: String = Gson().toJson(obj)
            Gson().fromJson(json, JsonObject::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * 解决Gson默认对Map中的int类型值处理成double类型的问题
 */
class CustomizedObjectTypeAdapter : TypeAdapter<Any?>() {
    private val delegate = Gson().getAdapter(
        Any::class.java
    )

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Any?) {
        delegate.write(out, value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Any? {
        val token = `in`.peek()
        return when (token) {
            JsonToken.BEGIN_ARRAY -> {
                val list: MutableList<Any?> = ArrayList()
                `in`.beginArray()
                while (`in`.hasNext()) {
                    list.add(read(`in`))
                }
                `in`.endArray()
                list
            }
            JsonToken.BEGIN_OBJECT -> {
                val map: MutableMap<String, Any?> = LinkedTreeMap()
                `in`.beginObject()
                while (`in`.hasNext()) {
                    map[`in`.nextName()] = read(`in`)
                }
                `in`.endObject()
                map
            }
            JsonToken.STRING -> `in`.nextString()
            JsonToken.NUMBER -> {
                // return in.nextDouble();
                val numberStr = `in`.nextString()
                if (numberStr.contains(".") || numberStr.contains("e") ||
                    numberStr.contains("E")
                ) {
                    return numberStr.toDouble()
                }
                if (numberStr.toLong() <= Int.MAX_VALUE) {
                    numberStr.toInt()
                } else {
                    numberStr.toLong()
                }
            }
            JsonToken.BOOLEAN -> `in`.nextBoolean()
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            else -> throw IllegalStateException()
        }
    }
}

