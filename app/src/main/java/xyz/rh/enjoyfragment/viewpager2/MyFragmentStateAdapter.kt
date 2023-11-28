package xyz.rh.enjoyfragment.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by rayxiong on 2022/10/30.
 */
class MyFragmentStateAdapter(fragmentAct: FragmentActivity): FragmentStateAdapter(fragmentAct) {

    var dataList: ArrayList<GaTab> = arrayListOf()

    fun setData(dataList: ArrayList<GaTab>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun createFragment(position: Int): Fragment {
        return dataList[position].fragment
    }
}