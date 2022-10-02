package com.enjoy.rib

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.enjoy.ribs.HomePresenter
import com.enjoy.ribs.IPresenterDelegate
import com.enjoy.ribs.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

 class VIPERFragment : Fragment(), IPresenterDelegate, View.OnClickListener {
//    private var param1: String? = null
//    private var param2: String? = null

    lateinit var mMapDetailTxt: TextView
    lateinit var mLocationTxt: TextView
    lateinit var mRootView: View
    lateinit var mMapCtrl: Button
    lateinit var mLocationCtrl: Button


     var mPresenter: HomePresenter<IPresenterDelegate>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_viper, container, false)
        mMapDetailTxt = mRootView.findViewById(R.id.map_detail)
        mLocationTxt = mRootView.findViewById(R.id.location_tv)
        mMapCtrl = mRootView.findViewById(R.id.ctl_map_btn)
        mLocationCtrl = mRootView.findViewById(R.id.ctl_location_btn)

        return mRootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VIPERFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VIPERFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         mPresenter = HomePresenter(this)

     }

     override fun moveMap(x: Int, y: Int) {
         mMapDetailTxt.text.apply {
             "$this x = $x, y=$y"
         }
     }

     override fun showMap() {
         Toast.makeText(context, "显示地图!",Toast.LENGTH_SHORT).show()
     }

     override fun hideMap() {
         Toast.makeText(context, "隐藏地图",Toast.LENGTH_SHORT).show()
     }

     override fun updateLocationView(location: String) {
         mLocationTxt.text.apply {
             "$this 地理位置变化了。。。"
         }
     }

     override fun onClick(v: View?) {
         if (v == mMapCtrl) {
         } else if (v == mLocationCtrl) {

         }
     }
 }