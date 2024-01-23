package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2023/3/11.
 */
class NewUserLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
        LayoutInflater.from(context).inflate(R.layout.test_merge_new_user_layout, this) as ConstraintLayout
        this.clipChildren
        // ViewGroup默认是不会执行onDraw的（性能优化），自定义ViewGroup需要强制走onDraw则需要执行setWillNotDraw(false)
        setWillNotDraw(false)

        setBackgroundColor(Color.BLUE)
        xlog("NewUserLayout:lifecycle constructor()")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xlog("NewUserLayout:lifecycle onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xlog("NewUserLayout:lifecycle onLayout()")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        xlog("NewUserLayout:lifecycle draw()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        xlog("NewUserLayout:lifecycle onDraw()  measureWidth = $measuredWidth, mesureHeight=$measuredHeight")
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        xlog("NewUserLayout:lifecycle dispatchDraw()")
    }






}