package xyz.rh.common

import android.content.Context
import android.util.TypedValue

/**
 * Created by rayxiong on 2023/4/23.
 */


lateinit var applicationContext: Context

fun setApplication(context: Context) {
    applicationContext = context
}

val Int.dp: Int get() = dp2px(this)

inline fun dp2px(value: Int): Int = dp2px(value.toFloat())

fun dp2px(value: Float): Int = applyDimension(value)

private fun applyDimension(value: Float): Int {
    val f = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value, applicationContext.resources.displayMetrics
    )
    val res = (if (f >= 0) f + 0.5f else f - 0.5f).toInt()
    if (res != 0) return res
    if (value == 0f) return 0
    return if (value > 0) 1 else -1
}
