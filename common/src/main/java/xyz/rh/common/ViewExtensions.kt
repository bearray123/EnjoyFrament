package xyz.rh.common

import android.view.View
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Created by rayxiong on 2023/1/11.
 */


private val TAG = R.id.view_auto_disposed_tag

val View.lifecycleScope: CoroutineScope
    get() {
        val checkExit = getTag(TAG) as? CoroutineScope
        if (checkExit != null) {
            return checkExit
        }
        val newScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate + AutoDisposeInterceptor(this))
        setTag(TAG, newScope)
        return newScope
    }


class AutoDisposeInterceptor(private var view: View) : ContinuationInterceptor {

    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        val job = continuation.context[Job]
        if (job != null) {
            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
                override fun onViewAttachedToWindow(v: View?) {
                }

                override fun onViewDetachedFromWindow(v: View?) {
                    view.removeOnAttachStateChangeListener(this)
                    job.cancel()
                }

            })
        }
        return continuation
    }

}

//class AttachStateWrapper(private val view: View, private val job: Job) : View.OnAttachStateChangeListener, CompletionHandler {
//    override fun onViewAttachedToWindow(v: View?) {
//        xlog("test disposed score::: onViewAttachedToWindow")
//
//    }
//
//    override fun onViewDetachedFromWindow(v: View?) {
//        xlog("test disposed score::: onViewDetachedFromWindow")
//        view.removeOnAttachStateChangeListener(this)
//        job.cancel()
//    }
//
//    // 协程执行完成清除listener
//    override fun invoke(cause: Throwable?) {
//        xlog("test disposed score::: CompletionHandler.invoke")
//        view.removeOnAttachStateChangeListener(this)
//        job.cancel()
//    }
//
//}



