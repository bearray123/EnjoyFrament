package xyz.rh.enjoyframent.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentTransaction
import xyz.rh.enjoyframent.Constants.GLOBAL_BACK_STACK_NAME
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.SecondFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DialogFragment : androidx.fragment.app.DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var jumpBtn: Button

    @ColorInt
    private var bgColor: Int? = null

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
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dialog, container, false)
        rootView.setBackgroundColor(bgColor!!)
        jumpBtn = rootView.findViewById<Button>(R.id.in_dialog_btn)
        jumpBtn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, SecondFragment())
                //这行决定从当前DialogFragment跳转到SecondFragment之后，按返回键,DialogFragment是否还继续显示
                transaction?.addToBackStack(GLOBAL_BACK_STACK_NAME)
                transaction?.commit()

            }
        })
        return rootView
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}