package xyz.rh.enjoyfragment.viewpager2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.rh.common.BaseFragment
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2022/10/30.
 */
class HonorOfKingsFragment : BaseFragment() {

//    companion object {
//        const val TAG = "HonorOfKingsFragment"
//    }

    lateinit var mRootView : View

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mRootView = inflater.inflate(R.layout.honor_king_fragment, container)
        return mRootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

}