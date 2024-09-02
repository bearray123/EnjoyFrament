package xyz.rh.enjoyfragment.coroutine

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

class TestCoroutineActivity : AppCompatActivity() {

    private val button1: Button by lazy {
        findViewById(R.id.button1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_coroutine)
        button1.setOnClickListener {
            xlog("TestCoroutineActivity==>点击了button1")
        }

        // 这个协程Scope的调度器是Main
//        val mainScope = MainScope()

        // 调度器是Dispatchers.Main.immediate: 1和4 是一个message，同属于onCreate的那个消息，3是另外单独的一个message
        // 此时打印顺序：1111 -> 4444 -> 2222 -> 3333

        // 调度器是Dispatchers.Main: 1和4不是同一个message，3是另外单独的一个message
        // 此时打印顺序：4444 -> 1111 -> 2222 -> 3333
        // 如果不申明调度器，默认是Main.immediate，具体可以查看lifecycleScope的源码，里面register时采用的是immediate
        lifecycleScope.launch {
            xlog("TestCoroutineActivity==>onCreate 1111")
            // 这里开始切线程，所以3就被切到另一个message中去了，3必然会等到这个切线程操作执行完成后才进一步执行
            withContext(Dispatchers.IO) {
                xlog("TestCoroutineActivity==>onCreate 2222")
                delay(3000)
            }
            xlog("TestCoroutineActivity==>onCreate 3333")
        }
        xlog("TestCoroutineActivity==>onCreate 4444")


    }
}

/*Dispatchers.Main和Dispatchers.Main.immediate核心区别：
1）不加 immediate:
当你在 Dispatchers.Main 上启动一个协程时，协程的执行通常是异步的。即使当前线程已经是主线程，它也会将这个协程调度为一个新的任务，放到事件队列中，然后稍后再执行。这意味着即使是在主线程上启动的协程，也不会立即执行，而是会在当前任务完成后才开始执行。
2）加上 immediate:
当你使用 Dispatchers.Main.immediate 时，如果你当前已经在主线程上，协程将会立即执行，不需要调度到事件队列中。这样做可以减少不必要的上下文切换和调度延迟，提升性能。如果当前线程不是主线程，那么它的行为和 Dispatchers.Main 是一样的，会将协程调度到主线程进行执行。*/
