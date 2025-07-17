package xyz.rh.common

import android.app.Activity
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
open class BaseFragment : Fragment() {

    private val TAG : String = this::class.java.simpleName


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "lifecycle:: onAttach() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "lifecycle:: onCreate() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "lifecycle:: onCreateView() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()} >>>>> window = ${(context as Activity).window}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "lifecycle:: onViewCreated() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "lifecycle:: onStart() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "lifecycle:: onResume() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "lifecycle:: onPause() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifecycle:: onStop() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "lifecycle:: onDestroyView() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "lifecycle:: onDestroy() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "lifecycle:: onDetach() >>>> ${this.javaClass.simpleName} >>> hashcode=${hashCode()}")
    }


    // 在viewpager切换的过程中onHiddenChanged是不会走到的，这个只有在add show hide过程中才会触发
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "lifecycle:: onHiddenChanged(), hidden = $hidden")
    }

    // todo by ray 奇怪，使用viewpager2 在tab切换过程中这个根本不会走到，需要深究
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "lifecycle:: setUserVisibleHint() , isVisibleToUser = $isVisibleToUser")

    }


}