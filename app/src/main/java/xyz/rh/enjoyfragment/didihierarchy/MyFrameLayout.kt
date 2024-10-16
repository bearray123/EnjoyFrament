package xyz.rh.enjoyfragment.didihierarchy

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2024/10/16.
 */
class MyFrameLayout(
    context: Context,
    attrs: AttributeSet? = null,
): FrameLayout(context, attrs) {

    private var name: String? = null

    fun setName(name: String?) {
        this.name = name
    }

    private var type: LayoutType? = null
    fun setLayoutType(type: LayoutType) {
        this.type = type
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var ret = super.onTouchEvent(event)

//        if (type?.id == 3) { // 如果是首页，进行消费
//            ret = true
//        }
        if (type?.id == 2) { // 如果是地图，进行消费
            ret = true
        }
        xlog("我是 ${type?.name}, 触发了 onTouchEvent()， 消费的结果：$ret")
        return ret
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var ret = super.onInterceptTouchEvent(ev)
        xlog("我是 ${type?.name}, 触发了 onInterceptTouchEvent()， 是否拦截的结果：$ret")
        return ret
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

}

class LayoutType(val id: Int, val name: String)

