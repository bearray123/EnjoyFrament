//package xyz.rh.enjoyfragment.scrollviewx
//
///**
// * Created by rayxiong on 2025/9/23.
// */
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import xyz.rh.enjoyfragment.R
//
//class SimpleTextAdapter(
//    private val data: List<String>
//) : RecyclerView.Adapter<SimpleTextAdapter.VH>() {
//
//    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tv: TextView = itemView.findViewById(R.id.tv)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val v = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_simple_text, parent, false)
//        return VH(v)
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.tv.text = data[position]
//    }
//
//    override fun getItemCount(): Int = data.size
//}
