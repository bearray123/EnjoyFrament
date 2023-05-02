package xyz.rh.enjoyframent.fragment.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.enjoyframent.R

/**
 * Created by rayxiong on 2023/2/28.
 */
class RHConstraintLayout  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val mRootView: View
    init {
        mRootView = LayoutInflater.from(context).inflate(R.layout.rh_constraint_layout, this, true)
    }

    val bottomViewStub = mRootView.findViewById<ViewStub>(R.id.bottom_view_layout_stub)

    fun update() {
        bottomViewStub.visibility = VISIBLE
        val textView = mRootView.findViewById<TextView>(R.id.textview_vb)
        textView.text = "调用update，进行更新"
    }


}