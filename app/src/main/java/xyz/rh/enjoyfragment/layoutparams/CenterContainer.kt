package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/2/28.
 */
class CenterContainer(val context: Context, root: ViewGroup) {

    // 必须传父view，否则这个view对应的layout文件通过inflate解析后根里面设置的layout_*系列属性都不生效，比如我这次遇到的是高度设置的是无效的！
//    凡是以 layout_ 开头的属性，都是“给父容器看的”布局参数（LayoutParams）。
//    这对 XML 里几乎所有子 View 都成立：android:layout_width/height、layout_margin*、layout_gravity、app:layout_constraint*、layout_weight……统统属于父容器定义的 LayoutParams，由父容器在布局阶段读取并生效。
    val rootView : View = LayoutInflater.from(context).inflate(R.layout.pickup_guide_info_window, root, false)
    // 原理解读：
//    为什么这么设计？
//    1.每个 ViewGroup 决定“怎么摆孩子”，因此它定义一套自己的 LayoutParams（如 LinearLayout.LayoutParams、FrameLayout.LayoutParams、ConstraintLayout.LayoutParams）。
//    2.这些 layout_* 属性在 inflate 时会被解析进父容器类型对应的 LayoutParams 对象里（通过 parent.generateLayoutParams(attrs)）。
//    3.布局时父容器会看孩子的 child.getLayoutParams() 来决定测量/放置方式。
//    4.对比“子自身属性”（由子 View 解析）：background、padding、elevation、alpha、clickable、id、contentDescription、textColor… 这些不带 layout_，是子 View 自己在构造里解析的。所以像backgroud等这类属性就算不传parent也能生效！
//
//    小结：android:layout_* 不是子 View 的固有属性，而是“交给父容器的摆放说明书”。


    val imageView = rootView.findViewById<ImageView>(R.id.qu_wait_map_pickup_guide_icon)

    fun setData(url: String) {
        Glide.with(context).load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : ImageViewTarget<Drawable?>(imageView) {

            override fun setResource(resource: Drawable?) {
                xlog("CenterContainer::: setResource ready, resource=$resource")
                resource?.let {
                    imageView.setImageDrawable(it)
                }
            }

        })
    }


}