package xyz.rh.enjoyfragment.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.*
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.MainActivity
import xyz.rh.enjoyfragment.R

/**
 * Created by xionglei01@baidu.com on 2022/10/11.
 */
class KotlinMainActivity : ComponentActivity() {

    companion object {
        var COUNT = 1
    }

    val mainScope = MainScope()

    var job1: Job? = null

    val button1 by lazy {
        findViewById<Button>(R.id.button1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_main_activity_layout)

        xlog("KotlinMainActivity::onCreate ===> this=$this")

//        button1.setOnClickListener {
//
//            mainScope.launch {
//
//                // 取数据要10秒
//                xlog("getData -----> start  ---->")
//                val response = getData()
//                xlog("getData -----> end  ----> $response")
//
//
//                startActivityForResult(Intent().apply {
//                    this.setClass(this@KotlinMainActivity, KotlinSecondActivity::class.java)
//                }, 999)
//
//
//                KotlinSecondActivity.callback = object : OnResultCallback {
//                    override fun onResult(ret: String) {
//
//                        Toast.makeText(this@KotlinMainActivity, "从Second页面回来了，返回的数据是：$ret", Toast.LENGTH_SHORT).show()
//
//                    }
//
//                }
//
//            }
//
//        }

        button1.setOnClickListener {
            if (COUNT++ <= 3) {
                Toast.makeText(this, "第${COUNT}次启动！", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@KotlinMainActivity, KotlinMainActivity::class.java))
            } else {
                Toast.makeText(this, "oh, 满了，需要启动Main了！", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@KotlinMainActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                })
            }

        }



    }

    suspend fun getData() : String {
        return withContext(Dispatchers.IO) {
            xlog("KotlinMainActivity:: withContext start")

            // 当前线程睡眠10秒
            xlog("KotlinMainActivity:: thread start.....")
//                Thread.sleep(10000) // 这里用Thread.sleep是无法通过scope.cancel取消
//                delay(10000) // 用delay挂起函数可以通过scope.cancel取消

            repeat(10) { count ->
                xlog("KotlinMainActivity:: count === $count")
                delay(1000)
            }

            xlog("KotlinMainActivity:: thread end.....")

            xlog("KotlinMainActivity:: withContext end!!!")

            "这是getData返回的数据"
        }
    }


    override fun onResume() {
        super.onResume()
        xlog("KotlinMainActivity:: onResume()")
//        job1 = mainScope.launch {
            xlog("KotlinMainActivity:: 111111111")
//            withContext(Dispatchers.IO) {
//                xlog("KotlinMainActivity:: withContext start")
//
//                // 当前线程睡眠10秒
//                xlog("KotlinMainActivity:: thread start.....")
////                Thread.sleep(10000) // 这里用Thread.sleep是无法通过scope.cancel取消
////                delay(10000) // 用delay挂起函数可以通过scope.cancel取消
//
//                repeat(10) { count ->
//                    xlog("KotlinMainActivity:: count === $count")
//                    delay(1000)
//                }
//
//                xlog("KotlinMainActivity:: thread end.....")
//
//                xlog("KotlinMainActivity:: withContext end!!!")
//            }
//        }
    }


    override fun onPause() {
        super.onPause()
        xlog("KotlinMainActivity:: onPause()")
        // 验证scope.cancel和job.cancel的区别！
        // 官方medium.com上对协程cancel的详细说明：https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
        // Once you cancel a scope, you won’t be able to launch new coroutines in the cancelled scope.

        xlog("KotlinMainActivity:: mainScope.cancel()")
        mainScope.cancel() // scope.cancel之后是无法再次launch的

//        xlog("KotlinMainActivity:: job.cancel()---> job = $job1")
//        job1?.cancel() // job.cancel之后是可以再次launch的

    }


    override fun onDestroy() {
        xlog("KotlinMainActivity:: onDestroy()")
        super.onDestroy()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999) {
            val toastData = "${data?.getStringExtra("result1")} === ${data?.getStringExtra("result2")}"
            Toast.makeText(this, "从Second页面回来了，返回的数据是：$toastData", Toast.LENGTH_SHORT).show()
        }
    }


}