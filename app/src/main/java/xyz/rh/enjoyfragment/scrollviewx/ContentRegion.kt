package xyz.rh.enjoyfragment.scrollviewx

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2025/9/23.
 */
class ContentRegion(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr)  {

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recycler_view)
    }

//    private var adapter: RecyclerView.Adapter<String>

    init {
        LayoutInflater.from(context).inflate(R.layout.scrollviewx_content_region_layout, this)
    }

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