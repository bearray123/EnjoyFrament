package xyz.rh.enjoyframent.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import xyz.rh.common.BaseFragment
import xyz.rh.enjoyframent.Constants
import xyz.rh.enjoyframent.R
import xyz.rh.enjoyframent.dialog.RHDialogFragment.Companion.newInstance
import xyz.rh.enjoyframent.fragment.ThirdFragment.Companion.newInstance

/**
 * Created by rayxiong on 2023/9/9.
 */
class FirstFragment : BaseFragment() {

    private var textView: TextView? = null
    private var firstImg: ImageView? = null
    private var mText: String? = null
    private var mShowDialogBtn: Button? = null
    private var mStartSubFragment: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val parent = parentFragment
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.fragment_first, container, false)
        Log.w(
            TAG,
            "XL::: FistFragment  RootView === $rootView, parentFragment == $parent$this"
        )
        textView = rootView.findViewById(R.id.textview_first)
        firstImg = rootView.findViewById(R.id.first_image)

        val fragmentList = parentFragmentManager.fragments
        val currentFragmentIndex = fragmentList.indexOf(this)
        val mode = arguments?.getString("mode")
        textView?.text = "${textView?.text} :: 启动模式 $mode ---> fragmentList.indexOf(this) = $currentFragmentIndex" // index从0开始的
        mShowDialogBtn = rootView.findViewById(R.id.show_dialog_btn)
        mShowDialogBtn?.setOnClickListener {
            // 方式一：采用DialogFragment来实现弹窗
            val dialogFragment = newInstance("", "")
            dialogFragment.setBgColor(Color.GREEN)

            // 测试fragment嵌套fragment时ChildFragmentManager的使用：
            // 如果外层fragment以ADD的模式启动，再以childFragmentManager启动fragmentDialog后，再以ADD的模式启动另一个fragment后 dialog会显示在最顶层！！！
            // 如果是replace的方式则childFragmentManager可以发挥正常，当fragment跳转后dialogFragment会被盖住：其实不是真盖住了，是因为replace启动fragment后会导致前一个fragment以及它包裹的dialogfragment走onDestroyView逻辑，
            // 再次返回后其实是重新走了一遍onCreateView->onStart 等流程，是重新创建了view

            // 这里在show时：如果传入的fragmentManager是getChildFragmentManager，则fragment跳走后当前fragmentDialog会正常被盖住
            // 如果传入的fragmentManager是activity.getSupportFragmentManager，在页面跳走后当前fragmentDialog不会消失，会覆盖到目标fragment之上
            dialogFragment.show(childFragmentManager, "")


            // 为什么 DialogFragment用dismiss好使，而用fragmentManager不好使？？？？
//            val transition = childFragmentManager.beginTransaction()
//            transition.hide(dialogFragment)
//            transition.commitAllowingStateLoss()
//            dialogFragment.dismiss()

        }
        mStartSubFragment = rootView.findViewById(R.id.start_sub_fragment)
        mStartSubFragment?.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            val subFragment = SecondFragment()
            subFragment.updateContent("作为子Fragment添加到FirstFragment里面")

            // 验证getChildFragmentManager的使用：
            // 在使用getChildFragmentManager去启动和管理一个子fragment时，整个视图层级都是处于当前这个Fragment下的
            // 对应的container也必须是当前Fragment里的ViewGroup，如果取Activity层里面的container会crash报错：
            // java.lang.IllegalArgumentException: No view found for id 0x7f0800d1 (xyz.rh.enjoyframent:id/fragment_container) for fragment SecondFragment
            // crash的原因就是fragment_container其实不在当前这个Fragment里
            //transaction.replace(R.id.fragment_container, subFragment);

            //transaction.replace(R.id.sub_fragment_container, subFragment);
            //transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
            //transaction.commit();
        }
        Log.w(TAG, "XL::: FistFragment  textView.getRootView() === ${textView?.rootView}")
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //////////////////////////////////////////////////////////////////
        // 在fragment中测试Glide图片加载，如果fragment已经销毁 或 detach了，使用Glide是啥样的效果
        // 敌法师图片:
        val antiImgUrl =
            "https://steamcdn-a.akamaihd.net/apps/dota2/images/dota_react/heroes/antimage.png"
        // 百度上巩俐性感照片： 这个图片大小是360KB，非常适合用来模拟charles慢网速图片加载的case，需要把charles throttle配置下： Download:200, upload:100  Utilisation: 100, 100
        val gongliSexyUrl =
            "https://pic.rmb.bdstatic.com/bjh/down/4e168e78e07d8f742ede564b7ac9b8d1.jpeg"

        // 重点知识点！！！
        // 对于Glide.with(View|Context|Activity|Fragment|FragmentActivity) 传入的类型大有文章，他可以是View，可以是fragment, 可以是 Activity，甚至可以是applicationContext，不同的类型决定了图片请求链路的生命周期：
        // 如果传入的是Activity，则当当前请求请求图片的fragment容器销毁（已经被detach），fragment所在Activity容器没有销毁时，请求链路不会终止，当数据完成后还会继续回调onResourceReady，因为链路的生命周期跟随当前with传入的activity生命周期
        // 此时如果在onResourceReady里操作fragment就会导致一些异常，例如getChildFragmentManager时出现Fragment has not been attached yet.
        // 换言之，如果with传入的是当前fragment，如果fragment被销毁（按返回键），图片的请求链路也随之停止，不会再回调onResourceReady
        // 所以：在使用Glide时一定要重点关注Glide.with（）传入对象是哪种，如果生命周期过长会导致图片回来后回调onResourceReady，然后在onResourceReady里操作生命周期过短（已经被销毁对象fragment）时出现一些异常

        // with的含义：首先，我们来看with，其实with的功能就是根据传入的context来获取图片请求管理器RequestManager，用来启动和管理图片请求。
        // context可以传入app，activity和fragment，这关系着图片请求的生命周期。通常使用当前页面的context，这样当我们打开一个页面加载图片，然后退出页面时，图片请求会跟随页面销毁而被取消，而不是继续加载浪费资源。
        // 当context是app时，获得的RequestManager是一个全局单例，图片请求的生命周期会跟随整个app。
        // 注意：如果with发生在子线程，不管context是谁，都返回应用级别的RequestManager单例。
        Glide.with(requireContext()) // 如果传getContext()，其实是host，即Activity，会出现fragment被销毁后，还会继续回调onResourceReady；
            //Glide.with(this) // 如果传this, 即当前fragment，则当fragment销毁后不会回调onResourceReady
            //Glide.with(firstImg) // 如果传当前加载的view，则当fragment销毁后其实view自身也就消化了，也不会回调onResourceReady
            .load(gongliSexyUrl)
            .skipMemoryCache(true) // 为了测试禁用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) // 为了是测试禁用磁盘缓存
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    // onResourceReady 在主线程回调
                    Log.d(TAG, "First ImageView:: onResourceReady()")
                    firstImg!!.setImageDrawable(resource)
                    // 通过charles 设置慢速网络，让图片经过好几秒才可以下载成功，在下载过程中按返回让当前fragment销毁，fragment已经被detach销毁后，还再继续getChildFragmentManager,最终导致FATAL EXCEPTION：main:
                    //java.lang.IllegalStateException: Fragment FirstFragment has not been attached yet.
                    //FragmentManager childFragmentManager = getChildFragmentManager();
                    //Log.d(TAG, "First ImageView:: onResourceReady() ====  getChildFragmentManager == " + childFragmentManager);
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.d(TAG, "First ImageView:: onLoadCleared()")
                }
            })
        //////////////////////////////////////////////////////////////////
        // 这里用空fragment只是想测试 以不带containerViewId来add一个fragment是什么效果：
        // 结论：空fragment可感知父fragment的生命周期，另外空fragment由于没有containerViewId其实是不会加到视图中去的！
        val childFm = childFragmentManager
        val fragmentTransaction = childFm.beginTransaction()
        fragmentTransaction.add(EmptyFragment(), "xyz.xionglei")
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        childFragmentManager.fragments.forEach {
            it.onHiddenChanged(hidden)
        }
    }

    /**
     * 在片段已与 Activity 关联时进行调用（Activity 传递到此方法内）。
     * @param context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    /**
     * 在取消片段与 Activity 的关联时进行调用。
     */
    override fun onDetach() {
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    /**
     * 在移除与片段关联的视图层次结构时进行调用。
     */
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun updateContent(text: String?) {
        mText = text
    }

    companion object {
        const val TAG = "FirstFragment"
    }
}