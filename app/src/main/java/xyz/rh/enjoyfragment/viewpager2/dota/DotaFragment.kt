package xyz.rh.enjoyfragment.viewpager2.dota

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R
import xyz.rh.common.BaseFragment

/**
 * Created by rayxiong on 2022/10/30.
 */
class DotaFragment : BaseFragment() {

    companion object {
        const val TAG = "DotaFragment"
    }

    lateinit var mRootView : View
    lateinit var mRecyclerView: RecyclerView

    lateinit var mListAdapter: ListAdapter<DotaHero, DotaHeroViewHolder>

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRootView = inflater.inflate(R.layout.dota_page_fragment, container)
        return mRootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = mRootView.findViewById<RecyclerView>(R.id.hero_list)
        // mark: 之前没设置layoutManager，所以列表一直没显示出来，设置layoutManager后就可以正常显示了!!!
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)
        mListAdapter = DotaRecycleViewAdapter(HeroDiffCallback())
        mRecyclerView.adapter = mListAdapter
//        mRecyclerView.itemAnimator = null



        // 获取分割线，复用listView里的
        val attrs = intArrayOf(android.R.attr.listDivider)
        val typeArray = context?.obtainStyledAttributes(attrs)
        val dividerLine = typeArray?.getDrawable(0)
        typeArray?.recycle()

        mRecyclerView.addItemDecoration(DivideDecoration(dividerLine!!))
//        mRecyclerView.addItemDecoration(BubbleDecoration())

        mRecyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            xlog("mRecyclerView =====>onGlobalLayout()")
        }

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                xlog("Dota::RecyclerView--->onScrollStateChanged: newState=${newState}")
                // 0 : IDLE
                // 1 : DRAGGING
                // 2 : SETTLING
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 0
//                    val heroItem = mListAdapter.currentList.find { hero ->
//                        hero.name == "npc_dota_hero_bane"
//                    }

                    // LayoutManager.findViewByPosition： 返回整个列表中对应位置的View
                    // RecyclerView.getChildAt  ： 这个方法返回的是屏幕可见的View，比如，getChildAt(1)返回屏幕上第2个可见的View
//                    val childIndex3 = mRecyclerView.getChildAt(3)

                    val childIndex3 = (mRecyclerView.layoutManager as LinearLayoutManager).findViewByPosition(3)

                    childIndex3?.run {
                        // 当这个childItem移除到RecyclerView之外后获取到的就是空了，所以要判空
                        val childVH = mRecyclerView.getChildViewHolder(childIndex3) as DotaHeroViewHolder
                        xlog("Dota::RecyclerView: childIndex3:: name = ${childVH.heroNameView.text} , top = ${childIndex3.top}, ${childIndex3.y}")
                    }
                }

            }

        })

        getAndUpdate(mListAdapter)

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable{
            override fun run() {
                xlog("test notifyDataSetChanged====")
                mListAdapter.notifyDataSetChanged()
            }

        }, 10_000)

    }


    fun getAndUpdate(adapter: ListAdapter<DotaHero, DotaHeroViewHolder>) {
        val repoApi = RepoApi()
        lifecycleScope.launch {
            val list2 = repoApi.getHeroFromDota2Api()
            xlog(TAG, "after get herolist , begin to submitList to recycleView:: herolist size =${list2?.size}")
            // 这里传给submitList的必须是不同的list对象，如果是同一个object内部执行时则会return出去
            adapter.submitList(list2)
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

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }





}