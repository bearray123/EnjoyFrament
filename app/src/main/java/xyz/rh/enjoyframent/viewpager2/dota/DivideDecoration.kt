package xyz.rh.enjoyframent.viewpager2.dota

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.common.xlog

/**
 * recyclerView的分割线
 * Created by rayxiong on 2023/4/22.
 */
class DivideDecoration(val lineDrawable: Drawable): RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawHorizontal(c, parent, state)


        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val itemView = parent.getChildAt(i)
            val itemViewHolder = parent.getChildViewHolder(itemView) as DotaHeroViewHolder
            if (itemViewHolder.heroNameView.text == "npc_dota_hero_bane") {
                xlog("check itemView:: npc_dota_hero_bane---> 瞄准的是痛苦之源: itemView.left = ${itemView.left}, itemView.top=${itemView.top}, itemView.right=${itemView.right}, itemView.bottom=${itemView.bottom}")
            }
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //画水平线
        // outRect： left, top, right, bottom的含义相当于是当前item在parent里的padding, 相当于是给item的parent加了padding的效果，但实际上并不会改变parent的padding值，只是在item布局时会把这部分outRect计算进去
        outRect.set(40, 100, 8, 50);

    }

    private fun drawHorizontal(c: Canvas , parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i);
            val params = child.layoutParams as RecyclerView.LayoutParams
            xlog("========== params.leftMargin = ${params.leftMargin}, params.topMargin=${params.topMargin}, params.rightMargin=${params.rightMargin}, params.bottomMargin=${params.bottomMargin}")
            val left = child.left - params.leftMargin
            val top = child.bottom + params.bottomMargin
            val right = child.right + params.rightMargin
            val bottom = top + lineDrawable.intrinsicHeight
            lineDrawable.setBounds(left, top, right, bottom)
            lineDrawable.draw(c)
        }
    }



}