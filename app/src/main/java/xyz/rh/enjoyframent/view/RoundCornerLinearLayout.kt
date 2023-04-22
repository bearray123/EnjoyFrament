package xyz.rh.enjoyframent.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * 可以设置圆角的线性布局
 * Created by rayxiong on 2023/2/28.
 */
class RoundCornerLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    private var paint: Paint
    private var roundWidth = 25
    private var roundHeight = 25
    private var paint2: Paint

    //控制四个圆角是否显示
    private var showLeftUp = true
    private var showLeftDown = true
    private var showRightUp = true
    private var showRightDown = true


    init {
//        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
//            roundWidth = a.getDimensionPixelSize(R.styleable.RoundImageView_roundWidth, roundWidth)
//            roundHeight =
//                a.getDimensionPixelSize(R.styleable.RoundImageView_roundHeight, roundHeight)
//            showLeftDown = a.getBoolean(R.styleable.RoundImageView_showLeftDown, showLeftDown)
//            showLeftUp = a.getBoolean(R.styleable.RoundImageView_showLeftUp, showLeftUp)
//            showRightUp = a.getBoolean(R.styleable.RoundImageView_showRightUp, showRightUp)
//            showRightDown = a.getBoolean(R.styleable.RoundImageView_showRightDown, showRightDown)
//            a.recycle()
//        } else {
//            val density = context.resources.displayMetrics.density
//            roundWidth = (roundWidth.toFloat() * density).toInt()
//            roundHeight = (roundHeight.toFloat() * density).toInt()
//        }
        paint = Paint()
        paint.setColor(-1)
        paint.setAntiAlias(true)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        paint2 = Paint()
        paint2.setXfermode(null as Xfermode?)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun draw(canvas: Canvas) {
        if (width <= 0 || height <= 0) {
            super.draw(canvas)
        } else {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas2 = Canvas(bitmap)
            super.draw(canvas2)
            if (showLeftUp) {
                drawLeftUp(canvas2)
            }
            if (showLeftDown) {
                drawLeftDown(canvas2)
            }
            if (showRightDown) {
                drawRightDown(canvas2)
            }
            if (showRightUp) {
                drawRightUp(canvas2)
            }
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint2)
            bitmap.recycle()
        }
    }

    private fun drawLeftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0.0f, roundHeight.toFloat())
        path.lineTo(0.0f, 0.0f)
        path.lineTo(roundWidth.toFloat(), 0.0f)
        path.arcTo(
            RectF(0.0f, 0.0f, (roundWidth * 2).toFloat(), (roundHeight * 2).toFloat()),
            -90.0f,
            -90.0f
        )
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawLeftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0.0f, (height - roundHeight).toFloat())
        path.lineTo(0.0f, height.toFloat())
        path.lineTo(roundWidth.toFloat(), height.toFloat())
        path.arcTo(
            RectF(
                0.0f,
                (height - roundHeight * 2).toFloat(),
                (roundWidth * 2).toFloat(),
                height.toFloat()
            ), 90.0f, 90.0f
        )
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo((width - roundWidth).toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), (height - roundHeight).toFloat())
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(),
                (height - roundHeight * 2).toFloat(),
                width.toFloat(),
                height.toFloat()
            ), 0.0f, 90.0f
        )
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), roundHeight.toFloat())
        path.lineTo(width.toFloat(), 0.0f)
        path.lineTo((width - roundWidth).toFloat(), 0.0f)
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(),
                0.0f,
                width.toFloat(),
                (0 + roundHeight * 2).toFloat()
            ), -90.0f, 90.0f
        )
        path.close()
        canvas.drawPath(path, paint)
    }



}