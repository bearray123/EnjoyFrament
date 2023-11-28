package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
    }


}