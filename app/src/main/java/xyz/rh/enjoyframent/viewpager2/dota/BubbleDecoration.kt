package xyz.rh.enjoyframent.viewpager2.dota

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.common.dp
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2023/4/22.
 */
class BubbleDecoration: RecyclerView.ItemDecoration() {

//    val mMaskBitmap = BitmapFactory.decodeResource(getResources(), if (arrowUp)R.drawable.bg_pop_tip_mask_up_arrow else R.drawable.bg_pop_tip_mask)
//    val mNinePatch = NinePatch(mMaskBitmap, mMaskBitmap.getNinePatchChunk(), null)

    private val paint = Paint()
    init {
        // 画笔宽度
        paint.strokeWidth = 0f
        paint.style = Paint.Style.FILL
        // 抗锯齿
        // 抗锯齿
        paint.isAntiAlias = true
        // 画笔颜色
        // 画笔颜色
        paint.color = Color.GREEN
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        xlog("BubbleDecoration:: onDraw")
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        xlog("BubbleDecoration:: onDrawOver")
        super.onDrawOver(c, parent, state)

        val childCount = parent.childCount
        var child: View
        for (i in 0 until childCount) {
            child = parent.getChildAt(i)
            val childViewHolder = parent.getChildViewHolder(child) as DotaHeroViewHolder
            // 画一个宽 600， 高 100 的rect
            val left = child.width - 600
            val right = left + 600
            xlog("childViewHolder.checkboxView.top == ${childViewHolder.checkboxView.top}")
            val top = child.top + (childViewHolder.checkboxView.parent as View).top - 123
            val bottom = top + 123

            c.save()
            val rect = Rect(left, top, right, bottom)
            c.drawRect(rect, paint)
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        xlog("BubbleDecoration:: getItemOffsets")
        outRect.set(10, 20, 10, 5)
    }

}