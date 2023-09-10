package xyz.rh.enjoyframent.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import xyz.rh.common.BaseFragment
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.MainActivity
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.fragment.model.GameHeroViewModel

class ThirdFragment : BaseFragment() {


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
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_third, container, false) as ViewGroup
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "我是 ${this.javaClass.simpleName}, 所在的容器是container_2 :: $this"

//        textView.postDelayed(object : Runnable {
//            override fun run() {
//
//                xlog("test disposed score::: post delay to remove the textview")
//
//                rootView.removeView(textView)
//
//
//            }
//
//        }, 5000)
//
//        val job = textView.lifecycleScope.launch {
//
//            withContext(Dispatchers.IO) {
//                xlog("test disposed score::: withContext start delay 10000")
//
//                delay(10000)
//
//                xlog("test disposed score:::  withContext delay end!")
//
//            }
//
//        }
//
//        job.invokeOnCompletion {
//            xlog("test disposed score:::  job.invokeOnCompletion  execute()")
//        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        val gameHeroViewModel = ViewModelProvider(this)[GameHeroViewModel::class.java]
        Log.d(FirstFragment.TAG, "print view model:: $gameHeroViewModel")


        xlog("Fragment::test coroutine scope:: onResume Fragment is = $this")

        lifecycleScope.launch {

            xlog("Fragment::test coroutine scope:: launch start")
            withContext(Dispatchers.IO) {
                xlog("Fragment::test coroutine scope:: withContext(Dispatchers.IO) start")
                delay(10_000)
                xlog("Fragment::test coroutine scope:: withContext(Dispatchers.IO) end!")
            }

        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        xlog("Fragment::test coroutine scope:: onDestroyView lifecycleScope.cancel()")
        lifecycleScope.cancel()
        MainScope()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }



}