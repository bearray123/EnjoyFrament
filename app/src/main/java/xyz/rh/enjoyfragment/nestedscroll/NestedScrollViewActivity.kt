package xyz.rh.enjoyfragment.nestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R

/**
 * 练手掌握NestedScrolling协议
 * 最佳实践还是要走系统自带的组件,比如参考 ViewPager2EntryActivity 里的内容
 */
class NestedScrollViewActivity : BaseActivity() {

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_xactivity)

        // 长列表内容区域：
        val dataList = listOf<String>(
            "敌法师","斯拉克","亚巴顿","炼金术士","远古冰魄","天穹守望者","斧王","祸乱之源","蝙蝠骑士","兽王","酒仙","钢背兽","半人马战行者","混沌骑士","发条技师","破晓辰星","末日使者","龙骑士","大地之灵","撼地者","上古巨神","哈斯卡","艾欧","昆卡","军团指挥官","噬魂鬼","狼人","马格纳斯","玛尔斯","玛西","石鳞剑士","帕吉","沙王","斯拉达","裂魂人","斯温","潮汐猎人","伐木机","小小","树精卫士","巨牙海民","孽主","不朽尸王","冥魂大帝","主宰","克林克兹","冥界亚龙","剃刀","剧毒术士","力丸","卓尔游侠","变体精灵","司夜刺客","圣堂刺客","复仇之魂","天穹守望者","娜迦海妖","巨魔战将","幻影刺客","幻影长矛手","幽鬼","影魔","德鲁伊","恐怖利刃","森海飞霞","灰烬之灵","熊战士","狙击手","矮人直升机","米拉娜","米波","编织者","美杜莎","育母蜘蛛","虚空假面","血魔","赏金猎人","露娜","齐天大圣","修补匠","先知","光之守卫","天怒法师","天涯墨客","宙斯","寒冬飞龙","工程师","巫医","巫妖","帕克","帕格纳","干扰者","戴泽","拉席克","拉比克","暗影恶魔","暗影萨满","术士","杰奇洛","死亡先知","殁境神蚀者","水晶室女","沉默术士","琼英碧灵","痛苦女王","瘟疫法师","祈求者","神谕者","维萨吉","莉娜","莱恩","虚无之灵","蝙蝠骑士","谜团","远古冰魄","邪影芳灵","陈","风暴之灵","风行者","食人魔魔法师","魅惑魔女","黑暗贤者",
            "电炎绝手","百戏大王","兽"
        )
        val adapter = StringListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.submitList(dataList) // 后续更新直接 submitList(newList)
        adapter.notifyDataSetChanged()

    }




}


class StringListAdapter
    : ListAdapter<String, StringListAdapter.VH>(DIFF) {

    class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tv.text = getItem(position)
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<String>() {
            // 若列表可能含重复字符串，最好用“带唯一id的数据结构”替代纯 String
            override fun areItemsTheSame(o: String, n: String) = o == n
            override fun areContentsTheSame(o: String, n: String) = o == n
        }
    }
}

