package xyz.rh.enjoyfragment.viewpager2.csgo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.common.BaseFragment
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2022/10/30.
 */
class CSGOFragment : BaseFragment() {

//    companion object {
//        const val TAG = "CSGOFragment"
//    }

    lateinit var mRootView : View

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.csgo_fragment, container, false)

        viewManager = LinearLayoutManager(context)

        val dataList = mutableListOf<String>()
        repeat(1000) {
            dataList.add("Item $it")
        }
        viewAdapter = CSGOAdapter(dataList)
        recyclerView = view.findViewById<RecyclerView>(R.id.myRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
//            addItemDecoration(RecyclerView.ItemDecoration())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
