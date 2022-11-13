package xyz.rh.enjoyframent.kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

/**
 * Created by xionglei01@baidu.com on 2022/10/11.
 */
class KotlinMainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val ff = AbcFragment()
//        val entity1 = ff.getData<EntityA>()
//        val entity2 = ff.getData<EntityB>()

    }

}


fun main() {

    println(123)

    val queue = LinkedBlockingDeque<String>()


    val thread1 = thread {

        var count = 1

        while (true) {

            Thread.sleep(9000)

            queue.add("data ${count++}")

        }

    }

    val thread2 = thread {
        while (true) {
            val data = queue.pollFirst()
            println(" 拿到了数据 ： ${data}")
            Thread.sleep(1000)
        }
    }


}