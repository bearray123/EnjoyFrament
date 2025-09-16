package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2025/8/14.
 */
class CustomViewContainer : ConstraintLayout {

     constructor(context: Context?) : super(context!!){

          xlog("QUCommonVideoViewV3:: init 构造器执行")
          setBackgroundColor(Color.RED)
          layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
          minWidth = 18
          minHeight = 12

     }

}