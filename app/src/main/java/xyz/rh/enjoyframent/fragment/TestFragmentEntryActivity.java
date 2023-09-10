package xyz.rh.enjoyframent.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.LinkedList;
import java.util.List;
import xyz.rh.enjoyframent.BaseActivity;
import xyz.rh.enjoyframent.R;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class TestFragmentEntryActivity extends BaseActivity implements View.OnClickListener {


    public static final String TAG = "FragmentEntryActivity";

    private View btn1, btn2, btn3, btn4;

    private TextView mBackStackContent1;

    private LinkedList<Integer> backStackList = new LinkedList<>();

    @SuppressLint("LongLogTag") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_entry_activity_main);

        // !!!视图栈管理器必须在最开始进行初始化：赋值fragmentManager 和 fragment的容器
        NavigationManager.init(this.getSupportFragmentManager(), R.id.fragment_container);

        btn1 = findViewById(R.id.changeBtn1);
        btn2 = findViewById(R.id.changeBtn2);
        btn3 = findViewById(R.id.changeBtn3);
        btn4 = findViewById(R.id.changeBtn4);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        // 测试APP切后台后能否执行fragment跳转：
        // 结论：不行，会崩溃；  除非忽略状态保存，使用commitAllowingStateLoss是可以跳转的，状态会走到onViewCreated，APP切前台后会走onStart,onResume
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        //at androidx.fragment.app.FragmentManager.checkStateLoss(FragmentManager.java:1844)
        //at androidx.fragment.app.FragmentManager.enqueueAction(FragmentManager.java:1884)
        //at androidx.fragment.app.BackStackRecord.commitInternal(BackStackRecord.java:329)
        //at androidx.fragment.app.BackStackRecord.commit(BackStackRecord.java:294)
        //at xyz.rh.enjoyframent.fragment.TestFragmentEntryActivity.changeFragment(TestFragmentEntryActivity.java:265)
        //at xyz.rh.enjoyframent.fragment.TestFragmentEntryActivity.onClick(TestFragmentEntryActivity.java:104)
        //at android.view.View.performClick(View.java:7281)

        //new Handler().postDelayed(new Runnable() {
        //    @Override public void run() {
        //        Log.d(TAG, "postDelayed=== performClick 2222");
        //        btn2.performClick();
        //    }
        //}, 5000);


        mBackStackContent1 = findViewById(R.id.backstack_content1);

        Fragment.class.isAssignableFrom(Object.class);

        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
                @Override public void onBackStackChanged() {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    List<Fragment> fragmentList = fragmentManager.getFragments();
                    Log.d(TAG, "onBackStackChanged========" + ", fragmentList size = " + fragmentList.size() + ", fragmentManager.getBackStackEntryCount = " + fragmentManager.getBackStackEntryCount());
                    updateBackStackContent();
                }
            });

        // Activity里 非Fragment中的view.getRootView == DecorView
        // Frament里view.getRootView == Fragment的根布局节点
        Log.w(TAG, "XL::: Activity :: getDecorView() === " + getWindow().getDecorView());
        Log.w(TAG, "XL::: Activity :: btn1.getRootView === " + btn1.getRootView());
        Log.w(TAG, "window:: getWindow()=== " + getWindow());



    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override public void onClick(View v) {

        if (v == btn1) {
            FirstFragment fragment = new FirstFragment();
            fragment.updateContent(fragment.hashCode() + "::加入回退栈");
            NavigationManager.push(fragment, NavigationManager.REPLACE, true);
        } else if (v == btn2) {
            SecondFragment fragment = new SecondFragment();
            fragment.updateContent(fragment.hashCode() + "::加入回退栈");
            //changeFragment(fragment, RELEACE, true);
            NavigationManager.push(fragment, NavigationManager.REPLACE, true);
        } else if (v == btn3) {
            ThirdFragment fragment = new ThirdFragment();
            //changeFragment(fragment, RELEACE, true);

            // 目前遇到的疑惑：
            // replace + addbackstack跳转到A,  然后replace 跳转到B， 此时回退栈只有A，再继续relace + addbackstack跳转A，此时回退栈里有两个A
            // 此时按返回键 会回到B，为什么会回到B，而且B会走onViewCreated,onResume很奇怪，B不是没加到回退栈吗？为什么还回到了B

            // 针对这个问题进一步简化流程，只做两部跳转，看生命周期：
            // 1、replace + addbackstack跳转到A   2、然后replace 跳转到B
            // 此时界面显示的是B，然后按返回键，界面无任何变化，还是显示的B，看生命周期回调发现调用了A的 onDestroy和onDetach，B的生命周期无任何变化，也就是说在B页面按返回键时是把A给销毁了
            // 通过这个简单验证得出结论： 回退栈真的只是针对在回退栈里元素的操作，这个case中回退栈里只有A，B不在回退栈，所以就算在B页面按返回键时处理的也只是A页面。
            // 所以通过这个简化的case可以再进一步分析上面稍微复杂的 A(在回退栈)-> B -> A(在回退栈) 的问题，当停留在后一个A时按返回键，B中的view会走重建。感觉回退栈根本界面显示哪个fragment没什么关系，
            // 回退栈处理的是返回键的逻辑，当一个activity中fragment的回退栈为空时返回键的逻辑就交给了activity自己处理了，自然也就关闭了activity，但是此时还是无法解释为什么B的view会重建？？？B是通过replace跳转到A的，并且最开始跳转到B时是不加回退栈的。
            // B中的View应该不会再重建才对

            // 第二天重新理解了下，感觉可以说通了，现在总结下：
            // 先从回退栈的本质说起，回退栈到底针对的是什么？其实针对的是那一次commit进行回滚操作，那B跳转到A时使用的是replace+addbackstack，
            // 而relace = remove（B）+ add（A），这一跳转其实是两个option，那么在A页面按返回键时进行的回退栈回滚的是remove（A）+ add（B）
            // 所以按返回键相当于是把A移除了，然后重新把B显示出来，自然也就走到了B的onCreateView,onViewCreated,onResume等生命周期

            FragmentManager fragmentManager =  getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //fragmentManager.popBackStack();  // 所谓的popBackStack，顾名思义就是pop回退栈里的元素
            //transaction.remove(cacheFragment);
            //transaction.detach(cacheFragment); // detach并不会导致fragment销毁走onDestroy和onDetach，只会走到onDestroyView，具体看该方法的官方注释

            // 通过把ThirdFragment使用单独的容器来验证容器和回退栈的关系，结论如下：
            // 当把ThirdFragment使用单独容器时，回退栈里的内容是不区分容器的，会返回所有容器的size，
            // 例如fragmentManager.getBackStackEntryCount()返回的是所有容器（包括fragment_container和fragment_container_2）里的count
            transaction.replace(R.id.fragment_container_2, fragment); // ThirdFragment单独使用另一个容器2
            transaction.addToBackStack("ThirdFrag");
            transaction.commit();


        } else if (v == btn4) {
            //popBackStackByIndex(2);
            FragmentManager fragmentManager =  getSupportFragmentManager();
            fragmentManager.popBackStack(1, POP_BACK_STACK_INCLUSIVE);
        }

        // 这里查看回退栈其实是有延时的，只能看到上一次的状态，所以不要在这里查看，需要放到onBackStackChanged里去监听查看
        //updateBackStackContent();


    }

    @Override public void onBackPressed() {
        super.onBackPressed();
    }

    private void popBackStackByIndex(int stackIndex) {
        FragmentManager fragmentManager =  getSupportFragmentManager();
        if (stackIndex < backStackList.size()) {
            int id = backStackList.get(stackIndex);
            for (int index = 0; index <= stackIndex; index++) { // 因为下面用的是POP_BACK_STACK_INCLUSIVE，所以这里用<=，包含当前的这个
                backStackList.pop();
                Log.d(TAG, "backStackList pop::: " + index);
            }
            Log.d(TAG, "after popBackStackByIndex()======== backStateList.size===" + backStackList.size());
            fragmentManager.popBackStack(id, POP_BACK_STACK_INCLUSIVE);
        } else {
            Toast.makeText(this, "当前指定的index超出了范围", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBackStackContent() {
        FragmentManager fragmentManager =  getSupportFragmentManager();
        int backstackEntryCount = fragmentManager.getBackStackEntryCount();
        mBackStackContent1.setText(getResources().getString(R.string.backstack_container1_count_string, backstackEntryCount));
    }


}