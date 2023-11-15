package xyz.rh.enjoyframent.fragment

import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import xyz.rh.common.log
import xyz.rh.enjoyframent.Constants
import java.util.*

/**
 * Created by rayxiong on 2023/9/9.
 */
object NavigationManager {

    private val backStackList = LinkedList<Int>()

    private var pageContainerId : Int = 0

    lateinit var fragmentMananger: FragmentManager

    private var currentFragment: Fragment? = null

    const val REPLACE = 100
    const val ADD = 101
    const val SHOW = 102
    const val HIDE = 103
    const val REMOVE = 104

    @Target(AnnotationTarget.TYPE)
    @IntDef(
        REPLACE,
        ADD,
        SHOW,
        HIDE
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class NavigationMode


    @JvmStatic
    fun init(fm: FragmentManager, pageContainerId: Int) {
        fragmentMananger = fm
        this.pageContainerId = pageContainerId


        fragmentMananger.addOnBackStackChangedListener {
            val entryCount = fragmentMananger.backStackEntryCount
            val fragmentsListSize = fragmentMananger.fragments.size
            log("onBackStackChanged::---> backStackEntryCount = $entryCount, fragments size = $fragmentsListSize")
            currentFragment = if (entryCount >= 1) {
                fragmentMananger.fragments[entryCount - 1]
            } else {
                null
            }
        }
    }

    @JvmStatic
    fun push(targetFragment: Fragment, mode: @NavigationMode Int, addToBackStack: Boolean = true) {
        handlePageChanged(targetFragment, mode, addToBackStack)
    }

    @JvmStatic
    fun pop(targetFragment: Fragment, mode: @NavigationMode Int, addToBackStack: Boolean = true) {

    }

    private fun handlePageChanged(targetFragment: Fragment, mode: @NavigationMode Int, addToBackStack: Boolean = true) {
        /**
         * 每一个Activity对应着一个FragmentController，即对应着一个FragmentManager对象
         */
        /**
         * 每一个Activity对应着一个FragmentController，即对应着一个FragmentManager对象
         */
        val transaction = fragmentMananger.beginTransaction()

        // 测试 setMaxLifecycle 的使用
        // 一般使用add进行跳转后，当前fragment生命周期是不会有变化的，为了在add模式下当前fragment生命周期有变化，至少走onPause，可采取将当前fragment设置最大生命周期为STARTED

        // 测试 setMaxLifecycle 的使用
        // 一般使用add进行跳转后，当前fragment生命周期是不会有变化的，为了在add模式下当前fragment生命周期有变化，至少走onPause，可采取将当前fragment设置最大生命周期为STARTED
        getFragmentByIndex(1)?.let {
            if (getFragmentByIndex(1) != null) {
                fragmentMananger.beginTransaction()
                    .setMaxLifecycle(it, Lifecycle.State.STARTED)
                    .commitNow()
            }
        }

        /**
         * add只会将一个fragment添加到容器中。 假设您将FragmentA和FragmentB添加到容器中。
         * 容器将具有FragmentA和FragmentB，如果容器是FrameLayout，则将fragment一个添加在另一个之上。
         * replace将简单地替换容器顶部的一个fragment，
         * 因此，如果我创建了 FragmentC并 replace 顶部的 FragmentB，
         * 则FragmentB将被从容器中删除（执行onDestroy，除非您调用addToBackStack，仅执行onDestroyView），而FragmentC将位于顶部。
         */
        //transaction.add(pageContainerId, newFragment);
        //transaction.replace(pageContainerId, newFragment);

        when (mode) {
            REPLACE -> transaction.replace(
                pageContainerId,
                targetFragment,
                targetFragment::class.java.simpleName
            )
            ADD -> { // add模式：先把当前的hide，然后add目标fragment
                currentFragment?.let {
                    transaction.hide(it)
                }
                transaction.add(pageContainerId, targetFragment, targetFragment::class.java.simpleName)
                currentFragment = targetFragment
            }
            REMOVE -> transaction.remove(targetFragment)
            SHOW -> transaction.show(targetFragment)
            HIDE -> transaction.hide(targetFragment)
            else -> throw IllegalArgumentException("must put mode params!")
        }

        //transaction.remove(newFragment);
        //transaction.hide(newFragment);
        //transaction.show(newFragment);

        /**
         * newFragment 会替换目前在 R.id.fragment_container ID 所标识的布局容器中的任何片段（如有）。
         * 通过调用 addToBackStack()，您可以将替换事务保存到返回栈，以便用户能够通过按返回按钮撤消事务并回退到上一片段。
         */
        //transaction.replace(pageContainerId, newFragment);


        //transaction.remove(newFragment);
        //transaction.hide(newFragment);
        //transaction.show(newFragment);
        /**
         * newFragment 会替换目前在 R.id.fragment_container ID 所标识的布局容器中的任何片段（如有）。
         * 通过调用 addToBackStack()，您可以将替换事务保存到返回栈，以便用户能够通过按返回按钮撤消事务并回退到上一片段。
         */
        //transaction.replace(pageContainerId, newFragment);
        if (addToBackStack) {
            // 将fragment管理加入到回退栈，栈名可以传null
            transaction.addToBackStack(/*Constants.GLOBAL_BACK_STACK_NAME*/null)
        }
        val indetify = transaction.commitAllowingStateLoss()
        backStackList.push(indetify)
    }

    fun getTopFragment() : Fragment?{
        return currentFragment
    }


    // count = 1 代表栈顶fragment，即倒数第一个
    // count =2 代表倒数第二个
    private fun getFragmentByIndex(count: Int): Fragment? {
        val addedFragmentList = fragmentMananger.fragments
        var index = addedFragmentList.size - count
        if (index <= 0) {
            index = 0
        }
        return if (addedFragmentList.size >= 1) {
            addedFragmentList[index]
        } else {
            null
        }
    }


}