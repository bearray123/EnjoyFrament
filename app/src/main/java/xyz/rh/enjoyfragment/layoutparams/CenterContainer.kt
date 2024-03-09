package xyz.rh.enjoyfragment.layoutparams

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/2/28.
 */
class CenterContainer(val context: Context) {

    val rootView : View = LayoutInflater.from(context).inflate(R.layout.pickup_guide_info_window, null, false)

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