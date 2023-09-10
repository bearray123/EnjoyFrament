package xyz.rh.enjoyframent.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import xyz.rh.common.log
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.fragment.NavigationManager
import xyz.rh.enjoyframent.fragment.SecondFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 自定义dialogFragment
 */
class RHDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RHDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var jumpBtn: Button

    @ColorInt
    private var bgColor: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("RHDialogFragment::Lifecycle::onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * 具备Fragment特性的Dialog实现
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("RHDialogFragment::Lifecycle::onCreateView()")
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dialog, container, false)
        rootView.setBackgroundColor(bgColor!!)
        jumpBtn = rootView.findViewById<Button>(R.id.in_dialog_btn)
        jumpBtn.setOnClickListener {
            NavigationManager.push(
                SecondFragment(),
                NavigationManager.ADD
            )
        }
        rootView.findViewById<Button>(R.id.in_dialog_cancel).setOnClickListener {

            // 测试：dismiss() 和 dismissAllowingStateLoss()
            Handler(Looper.myLooper()!!).postDelayed({
                log("click cancel:: delay 5s to dismiss")
                //                    dismiss()
                // 如果APP切后台（fragment走了onPause后）进行dissmiss会报如下异常crash，这里用postdelay就是为了模拟app切后台后执行dismiss
                //                    2023-06-30 17:11:17.145 19409-19409/xyz.rh.enjoyframent E/AndroidRuntime: FATAL EXCEPTION: main
                //                    Process: xyz.rh.enjoyframent, PID: 19409
                //                    java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                //                    at androidx.fragment.app.FragmentManager.checkStateLoss(FragmentManager.java:1844)
                //                    at androidx.fragment.app.FragmentManager.enqueueAction(FragmentManager.java:1884)
                //                    at androidx.fragment.app.BackStackRecord.commitInternal(BackStackRecord.java:329)
                //                    at androidx.fragment.app.BackStackRecord.commit(BackStackRecord.java:294)
                //                    at androidx.fragment.app.DialogFragment.dismissInternal(DialogFragment.java:355)
                //                    at androidx.fragment.app.DialogFragment.dismiss(DialogFragment.java:307)
                //                    at xyz.rh.enjoyframent.dialog.RHDialogFragment$onCreateView$2$1.run(RHDialogFragment.kt:71)
                //                    at android.os.Handler.handleCallback(Handler.java:900)
                //                    at android.os.Handler.dispatchMessage(Handler.java:103)
                //                    at android.os.Looper.loop(Looper.java:219)
                //                    at android.app.ActivityThread.main(ActivityThread.java:8393)
                //                    at java.lang.reflect.Method.invoke(Native Method)
                //                    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:513)
                //                    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1055)

                // 不关注状态丢失的话建议使用dismissAllowingStateLoss
                dismissAllowingStateLoss()
            }, /*3000*/0)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("RHDialogFragment::Lifecycle::onViewCreated()")
    }

    override fun onResume() {
        super.onResume()
        log("RHDialogFragment::Lifecycle::onResume()")
        // context是Activity容器，对应的window和dialog本身对应的window是不一样的对象！
        log("RHDialogFragment:: ---> context.window = ${(context as Activity).window}, dialog?.window = ${dialog?.window}")
    }

    /**
     * 传统Dialog实现
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    fun setBgColor(color: Int) : androidx.fragment.app.DialogFragment{
        bgColor = color
        return this
    }

    override fun onHiddenChanged(hidden: Boolean) {
        val fragmentTransaction = this.parentFragmentManager.beginTransaction()
        if (hidden) {
//            fragmentTransaction.hide(this)
            dismiss()

        } else {
//            fragmentTransaction.show(this)
            show(this.parentFragmentManager,"")
        }
//        fragmentTransaction.commitAllowingStateLoss()

    }

    override fun onPause() {
        super.onPause()
        log("RHDialogFragment::Lifecycle::onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("RHDialogFragment::Lifecycle::onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("RHDialogFragment::Lifecycle::onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        log("RHDialogFragment::Lifecycle::onDetach()")
    }

}