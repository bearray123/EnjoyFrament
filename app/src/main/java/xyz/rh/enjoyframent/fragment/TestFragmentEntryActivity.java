package xyz.rh.enjoyframent.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;
import xyz.rh.enjoyframent.R;
import xyz.rh.enjoyframent.temp.TempTestKotlin;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static xyz.rh.enjoyframent.Constants.GLOBAL_BACK_STACK_NAME;

public class TestFragmentEntryActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "FragmentEntryActivity";

    private View btn1, btn2, btn3, btn4;
    private TextView mBackStackContentView;

    private LinkedList<Integer> backStackList = new LinkedList<>();

    @SuppressLint("LongLogTag") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_entry_activity_main);

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

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Log.d(TAG, "postDelayed=== performClick 2222");
                btn2.performClick();
            }
        }, 5000);


        mBackStackContentView = findViewById(R.id.backstack_content);

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

    private Fragment cacheFragment = null;

    @Override public void onClick(View v) {

        if (v == btn1) {
            FirstFragment fragment = new FirstFragment();
            fragment.updateContent(fragment.hashCode() + "::加入回退栈");
            changeFragment(fragment, RELEACE,true);
        } else if (v == btn2) {
            SecondFragment fragment = new SecondFragment();
            cacheFragment = fragment;
            fragment.updateContent(fragment.hashCode() + "::加入回退栈");
            changeFragment(fragment, RELEACE, true);
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
            transaction.replace(R.id.fragment_container, fragment); // ThirdFragment单独使用另一个容器
            transaction.addToBackStack("ThirdFrag");
            transaction.commit();


        } else if (v == btn4) {
            //popBackStackByIndex(2);

            FragmentManager fragmentManager =  getSupportFragmentManager();
            int c = fragmentManager.getBackStackEntryCount();
            fragmentManager.popBackStack(1, POP_BACK_STACK_INCLUSIVE);
            int c1 = fragmentManager.getBackStackEntryCount();
            List sss =fragmentManager.getFragments();
            String sa;
            new Handler().post(new Runnable() {
                @Override public void run() {
                    int c2 = fragmentManager.getBackStackEntryCount();
                    String sa;
                }
            });
        }

        // 这里查看回退栈其实是有延时的，只能看到上一次的状态，所以不要在这里查看，需要放到onBackStackChanged里去监听查看
        //updateBackStackContent();


    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager =  getSupportFragmentManager();
        // 测试 setMaxLifecycle 的使用
        // 一般使用add进行跳转后，当前fragment生命周期是不会有变化的，为了在add模式下当前fragment生命周期有变化，至少走onPause，可采取将当前fragment设置最大生命周期为STARTED
        // 所以返回时取倒数第二个（即目标fragment）来设置最大生命周期为RESUME
        if (getFragmentByIndex(2) != null) {
            fragmentManager.beginTransaction()
                .setMaxLifecycle(getFragmentByIndex(2), Lifecycle.State.RESUMED)
                .commitNow();
        }

    }

    // count = 1 代表栈顶fragment，即倒数第一个
    // count =2 代表倒数第二个
    private Fragment getFragmentByIndex(int count) {
        FragmentManager fragmentManager =  getSupportFragmentManager();
        List<Fragment> addedFragmentList = fragmentManager.getFragments();
        int index = addedFragmentList.size() - count;
        if (index <= 0) {
            index = 0;
        }
        if (addedFragmentList.size() >=1) {
            return addedFragmentList.get(index);
        } else {
            return null;
        }
    }

    public static final int RELEACE = 1;
    public static final int ADD = 2;
    public static final int REMOVE = 3;
    public static final int SHOW = 4;
    public static final int HIDE = 5;

    @IntDef({RELEACE,ADD,REMOVE,SHOW,HIDE})
    @Retention(RetentionPolicy.SOURCE)
    @interface TransactionMode{}



    private void changeFragment(Fragment newFragment, @TransactionMode int mode, boolean addBackStack) {

        /**
         * 每一个Activity对应着一个FragmentController，即对应着一个FragmentManager对象
         */
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

         // 测试 setMaxLifecycle 的使用
         // 一般使用add进行跳转后，当前fragment生命周期是不会有变化的，为了在add模式下当前fragment生命周期有变化，至少走onPause，可采取将当前fragment设置最大生命周期为STARTED
         if (getFragmentByIndex(1) != null) {
             fragmentManager.beginTransaction()
                 .setMaxLifecycle(getFragmentByIndex(1), Lifecycle.State.STARTED)
                 .commitNow();
         }

        /**
         * add只会将一个fragment添加到容器中。 假设您将FragmentA和FragmentB添加到容器中。
         * 容器将具有FragmentA和FragmentB，如果容器是FrameLayout，则将fragment一个添加在另一个之上。
         * replace将简单地替换容器顶部的一个fragment，
         * 因此，如果我创建了 FragmentC并 replace 顶部的 FragmentB，
         * 则FragmentB将被从容器中删除（执行onDestroy，除非您调用addToBackStack，仅执行onDestroyView），而FragmentC将位于顶部。
         */
        //transaction.add(R.id.fragment_container, newFragment);
        //transaction.replace(R.id.fragment_container, newFragment);

        switch (mode) {
            case RELEACE:
                transaction.replace(R.id.fragment_container, newFragment);
                break;
            case ADD:
                transaction.add(R.id.fragment_container, newFragment);
                break;
            case REMOVE:
                transaction.remove(newFragment);
                break;
            case SHOW:
                transaction.show(newFragment);
                break;
            case HIDE:
                transaction.hide(newFragment);
                break;
            default:
                throw new IllegalArgumentException("must put mode params!");
        }

        //transaction.remove(newFragment);
        //transaction.hide(newFragment);
        //transaction.show(newFragment);

        /**
         * newFragment 会替换目前在 R.id.fragment_container ID 所标识的布局容器中的任何片段（如有）。
         * 通过调用 addToBackStack()，您可以将替换事务保存到返回栈，以便用户能够通过按返回按钮撤消事务并回退到上一片段。
         */
        //transaction.replace(R.id.fragment_container, newFragment);

        if (addBackStack) {
            // 将fragment管理加入到回退栈，栈名可以传null
            transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
        }
        int indetify = transaction.commitAllowingStateLoss();
        backStackList.push(indetify);
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
        mBackStackContentView.setText("回退栈：" + backstackEntryCount);
    }



    //fun getCornerDrawable(
    //    @ColorInt startColor: Int,
    //    @ColorInt endColor: Int,
    //    topLeftRadius: Float,
    //    topRightRadius: Float,
    //    bottomRightRadius: Float,
    //    bottomLeftRadius: Float
    //): GradientDrawable {
    //
    //    var gradientDrawable = GradientDrawable(
    //        GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(startColor, endColor))
    //    gradientDrawable.cornerRadii =
    //        floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)
    //    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
    //
    //
    //    return gradientDrawable
    //}

}