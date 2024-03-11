package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ActionMode
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.rh.common.lifecycleScope
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2024/1/23.
 */
class ConstraintLayout2(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("View::lifecycle:ConstraintLayout2: onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("View::lifecycle:ConstraintLayout2: onLayout()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("View::lifecycle:ConstraintLayout2: onDraw()  measureWidth = $measuredWidth, mesureHeight=$measuredHeight")
    }

}