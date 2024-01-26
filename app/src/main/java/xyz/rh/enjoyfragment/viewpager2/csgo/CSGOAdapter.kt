package xyz.rh.enjoyfragment.viewpager2.csgo

/**
 * Created by rayxiong on 2024/1/26.
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.enjoyfragment.R

class CSGOAdapter(private val dataList: List<String>) : RecyclerView.Adapter<CSGOAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cs_go_item_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.textView.text = item
    }

    override fun getItemCount() = dataList.size


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.itemTextView)
    }


}
