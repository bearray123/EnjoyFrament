package xyz.rh.common

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
        Log.d(TAG, "lifecycle:: onAttach()  >>>> hashcode=${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "lifecycle:: onCreate()  >>>> hashcode=${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "lifecycle:: onCreateView()  >>>> hashcode=${hashCode()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "lifecycle:: onViewCreated()  >>>> hashcode=${hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "lifecycle:: onStart()  >>>> hashcode=${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "lifecycle:: onResume()  >>>> hashcode=${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "lifecycle:: onPause()  >>>> hashcode=${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifecycle:: onStop()  >>>> hashcode=${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "lifecycle:: onDestroyView()  >>>> hashcode=${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "lifecycle:: onDestroy()  >>>> hashcode=${hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "lifecycle:: onDetach()  >>>> hashcode=${hashCode()}")
    }


    // ???viewpager??????????????????onHiddenChanged????????????????????????????????????add show hide?????????????????????
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "lifecycle:: onHiddenChanged(), hidden = $hidden")
    }

    // todo by ray ???????????????viewpager2 ???tab??????????????????????????????????????????????????????
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "lifecycle:: setUserVisibleHint() , isVisibleToUser = $isVisibleToUser")

    }


}