package xyz.rh.enjoyfragment.fragment.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.enjoyfragment.R

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

}