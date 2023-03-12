package xyz.rh.enjoyframent.layoutparams

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.enjoyframent.R

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
    }


}