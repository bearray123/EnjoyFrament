package xyz.rh.enjoyfragment.scrollview

import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import xyz.rh.common.dp
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2023/7/17.
 */
class TestScrollActivity : BaseActivity() {

    private val longTextView: TextView by lazy {
        findViewById(R.id.long_text_view)
    }
    // 纵向scrollview
    private val outVerticalScrollView: ScrollView by lazy {
        findViewById(R.id.out_vertical_scroll_view)
    }

    // 横向scrollview
    private val outHorizontalScrollView: HorizontalScrollView by lazy {
        findViewById(R.id.out_horizontal_scroll_view)
    }

    private val blockView1 : View by lazy {
        findViewById(R.id.blocks1)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.test_scroll_activity_layout)

        blockView1.postDelayed({

            xlog("longTextView:: 间隔5秒，调整第一个view的高度到150dp")
            // 重新调整高度会只会导致这个view本身走onDraw重绘，其他block不会重绘
            // 一步到位调整高度
//            blockView1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150.dp)
            // 通过属性动画顺滑调整高度，有动画效果
            blockView1.animateHeight(100, 150)

        }, 5000)

        // 关于scrollX和scrollY正负的问题，一个简单记忆的方法是：按照正常阅览思维：
        // 手指向左滑动说明想看进一步看右边最新的，跟翻页看书一样，所以scrollX为正，相反为负
        // 手指向上滑动说明想进一步看下面的最新的，跟刷短视频一样，所以scrollY为正，相反为负
        // 总结来说就是 想进一步刷内容 则 x, y 为正
        longTextView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            xlog("longTextView::OnScrollChangeListener --> scrollX = $scrollX, scrollY=$scrollY")

        }

        outVerticalScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            xlog("纵向scrollView:: outVerticalScrollView::OnScrollChangeListener --> scrollX = $scrollX, scrollY=$scrollY")

        }

        outHorizontalScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            xlog("横向scrollView:: outHorizontalScrollView::OnScrollChangeListener --> scrollX = $scrollX, scrollY=$scrollY")

        }



    }

    // 用属性动画来达到把blockview高度扩大顺滑的效果
    fun View.animateHeight(fromDp: Int, toDp: Int, duration: Long = 250L) {
        val from = fromDp
        val to   = toDp
        ValueAnimator.ofInt(from, to).apply {
            this.duration = duration
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
            addUpdateListener { anim ->
                val h = anim.animatedValue as Int
                layoutParams = layoutParams.apply { height = h.dp }
                requestLayout() // 通知重新布局以生效
            }
            start()
        }
    }


}