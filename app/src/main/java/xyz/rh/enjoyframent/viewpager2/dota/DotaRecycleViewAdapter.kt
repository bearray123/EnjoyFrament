package xyz.rh.enjoyframent.viewpager2.dota

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.viewpager2.dota.DotaRecycleViewAdapter.Companion.TAG

/**
 * Created by rayxiong on 2022/10/30.
 */
///////////////// Fragment里的内容，由RecyclerView列表实现/////////////
class DotaRecycleViewAdapter(diffCallback: DiffUtil.ItemCallback<DotaHero>) : ListAdapter<DotaHero, DotaHeroViewHolder>(diffCallback) {

    companion object {
        const val TAG = "DotaRecycleViewAdapter"
    }

    lateinit var mItemRootView: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotaHeroViewHolder {

        Log.d(TAG, "lifecycle: onCreateViewHolder()")


        // ItemView
        mItemRootView = LayoutInflater.from(parent.context).inflate(R.layout.dota_hero_item_layout, parent, false)

        return DotaHeroViewHolder(mItemRootView)
    }

    override fun onBindViewHolder(holder: DotaHeroViewHolder, position: Int) {
        Log.d(TAG, "lifecycle: onBindViewHolder()")

        holder.bindView(getItem(position))
    }

    /// 因为使用的是ListAdapter，内部已经实现了getItemCount,所以只需要实现 onCreateViewHolder ， onBindViewHolder即可
//        val heroDataList: ArrayList<DotaHero> = arrayListOf()
//        fun updateData(dataList: ArrayList<DotaHero>) {
//            dataList.clear()
//            heroDataList.addAll(dataList)
//        }
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotaHeroViewHolder {
//            mItemRootView = LayoutInflater.from(parent.context).inflate(R.layout.dota_hero_item_layout, parent, false)
//            return DotaHeroViewHolder(mItemRootView   )
//        }
//        override fun onBindViewHolder(holder: DotaHeroViewHolder, position: Int) {
//            holder.bindView(heroDataList[position])
//        }
//        override fun getItemCount(): Int {
//            return heroDataList.size
//        }

}

class DotaHeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val heroLogoView : ImageView by lazy {
        itemView.findViewById(R.id.hero_img)
    }

    val heroNameView : TextView by lazy {
        itemView.findViewById(R.id.hero_name)
    }

    private val heroTypeView : TextView? by lazy {
        itemView.findViewById(R.id.hero_type)
    }

    private val heroDespView : TextView by lazy {
        itemView.findViewById(R.id.hero_desp)
    }

    val checkboxView : View by lazy {
        itemView.findViewById(R.id.hero_checkbox)
    }



    fun bindView(hero: DotaHero) {
        Log.d(TAG, "DotaRecycleViewAdapter::ViewHolder bindView()")
        hero.imgUrl?.let { url ->
            Glide.with(itemView.context).load(url).into(heroLogoView)
        }
        heroNameView.text = hero.name
        heroTypeView?.text = hero.type
        heroDespView?.text = hero.desp

//        Handler().postDelayed(object: Runnable {
//            override fun run() {
//                Log.d(TAG, "DotaRecycleViewAdapter::ViewHolder postDelayed==========")
//                heroDespView.text = "这是篡改之后的s"
//            }
//        }, 5000)
    }
}


/**
 * 计算每条item diff差异的实现类
 */
class HeroDiffCallback : DiffUtil.ItemCallback<DotaHero>() {

    companion object {
        const val TAG = "HeroDiffCallback"
    }

    override fun areItemsTheSame(p0: DotaHero, p1: DotaHero): Boolean {
        return p0.name == p1.name
    }

    override fun areContentsTheSame(p0: DotaHero, p1: DotaHero): Boolean {
        if (p0.name != p1.name) {
            return false
        }
        if (p0.desp != p1.desp) {
            return false
        }
        return true
    }

}