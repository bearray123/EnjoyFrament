package xyz.rh.enjoyframent.viewpager2.dota

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import xyz.rh.common.xlog
import xyz.rh.enjoyframent.R
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
        getAndUpdate(mListAdapter)

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