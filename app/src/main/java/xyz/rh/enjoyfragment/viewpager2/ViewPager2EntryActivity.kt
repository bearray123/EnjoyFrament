package xyz.rh.enjoyfragment.viewpager2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R
import xyz.rh.enjoyfragment.viewpager2.dota.DotaFragment

/**
 * Created by rayxiong on 2022/10/30.
 */
class ViewPager2EntryActivity : BaseActivity() {

    companion object {
        const val TAG = "ViewPager2EntryActivity"
    }


    val mViewPager: ViewPager2 by lazy {
        findViewById(R.id.host_view_pager)
    }

    val mTabLayout: TabLayout by lazy {
        findViewById(R.id.host_tab_layout)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "lifecycle:: onCreate()")

        setContentView(R.layout.viewpager2_activity_layout)

        val dotaTab = GaTab("DOTA", R.drawable.tab_icon_dota, DotaFragment())
        val csgoTab = GaTab("CSGO", R.drawable.tab_icon_csgo, CSGOFragment())
        val honorOfKingsTab = GaTab("王者荣耀", R.drawable.tab_honor_king, HonorOfKingsFragment())
        val peaceEiteTab = GaTab("和平精英", R.drawable.tab_icon_peace, PeaceEiteFragment())
        val redAlertTab = GaTab("红警3", R.drawable.tab_icon_alert, RedAlertFragment())

//        val dotaFragment = DotaFragment()
//        val csgoFragment = CSGOFragment()
//        val honorOfKingsFragment = HonorOfKingsFragment()
//        val peaceEiteFragment = PeaceEiteFragment()
//        val hotGameFragment = RedAlertFragment()

        val tabList = arrayListOf<GaTab>().apply {
            add(dotaTab)
            add(csgoTab)
            add(honorOfKingsTab)
            add(peaceEiteTab)
            add(redAlertTab)
        }


        // 设置离屏显示的page数
        mViewPager.offscreenPageLimit = 1
        val fragmentStateAdapter = MyFragmentStateAdapter(this)
        fragmentStateAdapter.dataList = tabList
        mViewPager.adapter = fragmentStateAdapter

        mTabLayout.setSelectedTabIndicatorColor(Color.CYAN)
        val tabLayoutMediator = TabLayoutMediator(mTabLayout, mViewPager) { tab, position ->
            val adapter = (mViewPager.adapter as? MyFragmentStateAdapter)
            tab.text = adapter?.dataList?.get(position)?.title
            adapter?.dataList?.get(position)?.icon?.let {
                tab.setIcon(it)
            }

        }
        tabLayoutMediator.attach()

        GlobalScope.launch(Dispatchers.Main) {

            Log.d("xl###", "GlobalScope.launch {  begin    thread == ${Thread.currentThread().name}")

            withContext(Dispatchers.Main) {
                Log.d("xl###", "withContext(Dispatchers.Main)    thread == ${Thread.currentThread().name}")
                test()
            }

            Log.d("xl###", "GlobalScope.launch {  end   thread == ${Thread.currentThread().name}")

        }


    }

        suspend fun test() {
            Log.d("xl###", "test()  begin ====       thread == ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                Log.d("xl###", "test()  doing    thread == ${Thread.currentThread().name}")
            }
        }


}