package xyz.rh.enjoyfragment.didihierarchy

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2024/10/16.
 */
class InsideView(context: Context): View(context) {

    init {
        setOnClickListener {  } // 让该view isClickable = true
        xlog("我是 InsideView, isClickable = $isClickable")
    }


    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val ret = super.dispatchTouchEvent(event)
        xlog("我是 InsideView, 触发了 dispatchTouchEvent()， 消费的结果：$ret")
        return ret
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var ret = super.onTouchEvent(event)
        ret = true
        xlog("我是 InsideView, 触发了 onTouchEvent()， 消费的结果：$ret")
        return ret
    }


}