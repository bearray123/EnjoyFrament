package xyz.rh.enjoyfragment.didihierarchy

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import xyz.rh.common.dp
import xyz.rh.common.getScreenHeight
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/10/16.
 */
class HomeFragment: Fragment() {

    private var mRootView: View? = null
    private var contentContainer: ConstraintLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context == null) return
        (mRootView as ConstraintLayout).addView(
            // 动态添加一个自定义View，看touch生命周期执行
            InsideView(requireContext()).apply {
                this.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                    height = 200.dp
                    width = 200.dp
                    marginStart = 100.dp
                    // // start to start of parent
                    startToStart = (mRootView as ConstraintLayout).id
                }
                setBackgroundColor(Color.GREEN)
            }
        )
        contentContainer = mRootView?.findViewById(R.id.content_container)
        contentContainer?.apply {
            layoutParams = layoutParams.apply {
                // 把核心内容区域的高度调整下：屏幕的40%
                height = (getScreenHeight(requireContext()) * (0.4f)).toInt()
                setBackgroundColor(Color.MAGENTA)
            }
        }
    }

}