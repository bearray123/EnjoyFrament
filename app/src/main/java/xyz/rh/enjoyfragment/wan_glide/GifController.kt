package xyz.rh.enjoyfragment.wan_glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import xyz.rh.common.xlog

/**
 * Created by rayxiong on 2025/9/26.
 */

enum class PlayPhase { ShowGif1FirstFrame, PlayGif1, PlayGif2 }

class GifController(
    private val imageView: ImageView,
    private val gif1Url: String,
    private val gif2Url: String
) {
    private var phase: PlayPhase = PlayPhase.ShowGif1FirstFrame

    fun init() {
        // 进入页面先显示 GIF1 的第一帧（静态）
        loadFirstFrame(gif1Url)
    }

    fun onClick() {
        when (phase) {
            PlayPhase.ShowGif1FirstFrame, PlayPhase.PlayGif2 -> {
                xlog("GifController:: 播放gif1")
                playGifOnce(gif1Url) {
                    // 播放完 GIF1，停在最后一帧
                    phase = PlayPhase.PlayGif1
                }
            }
            PlayPhase.PlayGif1 -> {
                xlog("GifController:: 播放gif2")
                playGifOnce(gif2Url) {
                    // 播放完 GIF2，停在最后一帧
                    phase = PlayPhase.PlayGif2
                }
            }
        }
    }

    private fun loadFirstFrame(resId: String) {
        // 切资源前先清空，避免显示旧帧
        Glide.with(imageView).clear(imageView)
        imageView.setImageDrawable(null)

        Glide.with(imageView)
            .asBitmap()  // 把 GIF 当静态图解码 -> 默认首帧
            .load(resId)
            .dontAnimate()  // 禁用过渡动画，避免闪旧帧
            .into(imageView)

        phase = PlayPhase.ShowGif1FirstFrame
    }

    private fun playGifOnce(resId: String, onEnd: () -> Unit) {
        // 切资源前先清空，避免显现上一张 GIF 的最后一帧
//        Glide.with(imageView).clear(imageView)
//        imageView.setImageDrawable(null)

        Glide.with(imageView)
            .asDrawable()
            .load(resId)
            // ✅解决2个gif播放过程中再次播放（第三次点击）第1张gif时会插入一帧第一张gif最后一帧的问题！不要用内存缓存，每次都从磁盘读数据构造全新的GifDrawable对象！
            // 核心原因是第一张gif之前播放完成后如果复用那个gifDrawable对象的话它那会儿是停留在最后一帧了!
            .skipMemoryCache(true)
            // 加listener只是为了打印观察本次的drawable对象，看看是否有变化，是否复用同一个对象
            // 经过验证，虽然走了内存缓存复用，但resource对象是全新的，经过查询，resource是会走新的单是clone出来的，内部的state是复用的
            // 内存缓存命中后，Glide 常会基于缓存的 ConstantState 派生新对象，所以你看到的 id 不同。
            // 但派生出的 GifDrawable 可能共享底层状态（如 GifState/GifFrameLoader），这也是为什么有时仍会表现出“保留上一帧”的现象。
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // 如果把上面的跳过内存缓存去掉的话，则这里打印的都是DATA_DISK_CACHE，走的磁盘缓存！
                    // 这里打印来源和对象id，也打印内部的stateId是相同的，constantState保存的就是这个对象内部的状态，包括当前帧等信息
                    xlog("onResourceReady src=$dataSource id=${System.identityHashCode(resource)}， stateId=${System.identityHashCode(resource?.constantState)}")
                    return false // 这里不要返回true，true代表消费了，就到达不了后面加的CustomTarget里去了
                }

            })
//            .dontAnimate() // 或者 .transition(DrawableTransitionOptions.withNoTransition())
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    xlog("print resourceDrawable = $resource")
                    val gif = (resource as? GifDrawable)
                    if (gif != null) {
                        // 关键：强制从第一帧开始，只播放一遍
                        gif.setLoopCount(1)

                        imageView.setImageDrawable(gif)

//                    resource.setStartFromFirstFrame(true)
                        gif.startFromFirstFrame()
                        // 监听播放结束（Animatable2Compat 回调）

                        gif.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {

                            override fun onAnimationStart(drawable: Drawable?) {
                                super.onAnimationStart(drawable)
                            }

                            override fun onAnimationEnd(drawable: Drawable?) {
                                super.onAnimationEnd(drawable)
                                // 播放结束时保持最后一帧（不需要额外处理），推进状态机
                                onEnd()
                                // 可选：注销回调，防止重复
                                gif.unregisterAnimationCallback(this)
                            }

                        })



                        gif.start()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}
