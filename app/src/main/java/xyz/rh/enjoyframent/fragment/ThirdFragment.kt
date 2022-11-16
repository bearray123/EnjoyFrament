package xyz.rh.enjoyframent.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyz.rh.enjoyframent.R

class ThirdFragment : Fragment() {


    private val textView: TextView by lazy {
        rootView.findViewById(R.id.textview_third)
    }

    lateinit var rootView: ViewGroup

    companion object {
        fun newInstance() = ThirdFragment()
        const val TAG = "ThirdFragment"
    }

    private lateinit var viewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w(TAG, "lifeCycle::onCreateView() === === ===" + hashCode())
        rootView = inflater.inflate(R.layout.fragment_third, container, false) as ViewGroup
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.w(TAG, "lifeCycle::onViewCreated() === === ===" + hashCode())
        textView.text = "我是 第三个 :: $this"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.w(TAG, "lifeCycle::onAttach() === === ===" + hashCode())
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "lifeCycle::onResume() === === ===" + hashCode())
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "lifeCycle::onPause() === === ===" + hashCode())
    }

    override fun onStop() {
        super.onStop()
        Log.w(TAG, "lifeCycle::onStop() === === ===" + hashCode())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.w(TAG, "lifeCycle::onDestroyView() === === === " + hashCode())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "lifeCycle::onDestroy() === === ===" + hashCode())

    }

    override fun onDetach() {
        super.onDetach()
        Log.w(TAG, "lifeCycle::onDetach() === === ===" + hashCode())
    }



}