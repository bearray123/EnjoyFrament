package xyz.rh.enjoyfragment.jsonparser.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by rayxiong on 2023/9/5.
 */
class ParseErrorSkipGsonTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T?>?): TypeAdapter<T?> {
        val adapter = gson.getDelegateAdapter(this, type)
        return object : TypeAdapter<T?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T?) {
                adapter.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(reader: JsonReader): T? {
                return try {
                    adapter.read(reader)
                } catch (e: Throwable) {
                    e.printStackTrace()
//                    reader.skipValue() // 这行加的不对， 虽然不会导致整个model报错，但是从hashmap之后都是null
                    if (reader.hasNext()) {
                        if (reader.peek() == JsonToken.NAME) {
                            reader.nextName()
                        } else {
                            reader.skipValue()
                        }
                    }
                    null
                }
            }
        }
    }
}
