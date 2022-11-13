package xyz.rh.enjoyframent.viewpager2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.rh.enjoyframent.R

/**
 * Created by rayxiong on 2022/10/30.
 */
class RedAlertFragment : Fragment() {

    companion object {
        const val TAG = "RedAlertFragment"
    }


    lateinit var mRootView : View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "lifecycle:: onAttach()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "lifecycle:: onCreateView()")

        mRootView = inflater.inflate(R.layout.red_alert_fragment, container)
        return mRootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "lifecycle:: onViewCreated()")

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
}