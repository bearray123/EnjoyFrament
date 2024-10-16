package xyz.rh.enjoyfragment.didihierarchy

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import xyz.rh.common.dp
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/10/16.
 */
class HomeFragment: Fragment() {

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (rootView as? ViewGroup)?.addView(context?.let {
            InsideView(it).apply {
                this.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT).apply {
                    marginStart = 100.dp
                    height = 200.dp
                    width = 200.dp
                }
                setBackgroundColor(Color.GREEN)
            }
        })
    }

}