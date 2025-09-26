package xyz.rh.enjoyfragment.scrollviewx

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by rayxiong on 2025/9/23.
 */
// 注意：自定义View 如果在XML里使用时虽然知道必须有两个参数（context, attrs）的构造器，但还必须加上 @JvmOverloads constructor 这个注解，否则会在解析XML里这个View时报如下错误：
// Caused by: java.lang.NoSuchMethodException: xyz.rh.enjoyfragment.scrollviewx.KingKongContainer.<init> [class android.content.Context, interface android.util.AttributeSet]
class KingKongContainer @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


}