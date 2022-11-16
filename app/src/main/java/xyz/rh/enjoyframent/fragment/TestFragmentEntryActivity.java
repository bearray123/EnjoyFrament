package xyz.rh.enjoyframent.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import xyz.rh.common.eventpublisher.BaseEventPublisher;
import xyz.rh.enjoyframent.R;

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


        mBackStackContentView = findViewById(R.id.backstack_content);

        Fragment.class.isAssignableFrom(Object.class);

        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
                @Override public void onBackStackChanged() {
                    Log.d(TAG, "onBackStackChanged()========");
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
            changeFragment(fragment, RELEACE, false);
        } else if (v == btn3) {
            ThirdFragment fragment = new ThirdFragment();
            //changeFragment(fragment, RELEACE, true);

            // 目前遇到的疑惑：
            // replace + addbackstack跳转到A,  然后replace 跳转到B， 此时回退栈只有A，再继续relace + addbackstack跳转A，此时回退栈里有两个A
            // 此时按返回键 会回到B，为什么会回到B，而且B会走onViewCreated,onResume很奇怪，B不是没加到回退栈吗？为什么还回到了B

            FragmentManager fragmentManager =  getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //fragmentManager.popBackStack();  // 所谓的popBackStack，顾名思义就是pop回退栈里的元素
            transaction.remove(cacheFragment);
            //transaction.detach(cacheFragment); // detach并不会导致fragment销毁走onDestroy和onDetach，只会走到onDestroyView，具体看该方法的官方注释

            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();


        } else if (v == btn4) {
            popBackStackByIndex(2);
        }

        // 这里查看回退栈其实是有延时的，只能看到上一次的状态，所以不要在这里查看，需要放到onBackStackChanged里去监听查看
        //updateBackStackContent();


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
        int indetify = transaction.commit();
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

}