package xyz.rh.enjoyframent.viewpager2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by rayxiong on 2022/12/20.
 */
open class BaseTabFragment : Fragment() {

    private val TAG : String = this::class.java.simpleName


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "lifecycle:: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "lifecycle:: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifecycle:: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "lifecycle:: onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "lifecycle:: onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "lifecycle:: onDetach()")
    }


    // 在viewpager切换的过程中onHiddenChanged是不会走到的，这个只有在add show hide过程中才会触发
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "lifecycle:: onHiddenChanged(), hidden = $hidden")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "lifecycle:: setUserVisibleHint() , isVisibleToUser = $isVisibleToUser")
    }


}