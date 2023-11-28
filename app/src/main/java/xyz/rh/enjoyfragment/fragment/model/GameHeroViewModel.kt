package xyz.rh.enjoyfragment.fragment.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import xyz.rh.common.xlog
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by rayxiong on 2023/1/6.
 */
class GameHeroViewModel : ViewModel() {


    var heroListLiveData: MutableLiveData<GameHero> = MutableLiveData()


    fun getHeroList() {
        viewModelScope.launch {
            val data = doGet()
            heroListLiveData.value = data // setValue 必须在主线程
        }
    }


    suspend fun doGet() : GameHero {
        return suspendCancellableCoroutine { cotinuation ->

            val worker =  thread {
                var timeCount = 0
                while (Thread.interrupted() && timeCount++ >= 10) {
                    try {
                        Thread.sleep(1000)
                        xlog("","正在获取数据:")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        cotinuation.resumeWithException(e)
                        break
                    }
                }
                xlog(msg = "获取数据完成!")
                cotinuation.resume(GameHero("小鱼人from网络", "敏捷型"))
            }

            cotinuation.invokeOnCancellation {
                worker.interrupt()
            }

        }
    }


}