package xyz.rh.enjoyfragment.scroll

import android.os.Build
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import xyz.rh.common.xlog
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2023/7/17.
 */
class TestScrollActivity : BaseActivity() {

    val longTextView: TextView by lazy {
        findViewById(R.id.long_text_view)
    }
    // 纵向scrollview
    val outVerticalScrollView: ScrollView by lazy {
        findViewById(R.id.out_vertical_scroll_view)
    }

    // 横向scrollview
    val outHorizontalScrollView: HorizontalScrollView by lazy {
        findViewById(R.id.out_horizontal_scroll_view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.test_scroll_activity_layout)

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




}