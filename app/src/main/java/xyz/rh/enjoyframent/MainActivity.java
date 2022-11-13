package xyz.rh.enjoyframent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import javax.inject.Inject;
import xyz.rh.common.eventpublisher.BaseEventPublisher;
import xyz.rh.enjoyframent.databinding.MainActivityLayoutBinding;
import xyz.rh.enjoyframent.di.test.BussA;
import xyz.rh.enjoyframent.di.test.BussBFrom3rdParty;
import xyz.rh.enjoyframent.di.test.DaggerBussComponent;
import xyz.rh.enjoyframent.fragment.TestFragmentEntryActivity;
import xyz.rh.enjoyframent.viewpager2.ViewPager2EntryActivity;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    public BussA mBussA;
    @Inject
    public BussBFrom3rdParty mBussB;

    public static final String TAG = "MainActivity";

    /**
     * viewBinding的使用
     * 自动生成的类个数和layout目录中布局文件个数是对应的
     * 其实可以在所有的类中都使用viewBinding，我这里只在首页使用了，其他的类中都可以使用
     */
    private MainActivityLayoutBinding _layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerBussComponent.create().injectMainActivity(this);

        /////////////////////// test Lifecycle ////////////////////////////
        getLifecycle().addObserver(new LifecycleEventObserver() {

            @Override public void onStateChanged(@NonNull LifecycleOwner source,
                @NonNull Lifecycle.Event event) {
                Log.d(TAG, "LifecycleEventObserver:: " + event);
            }
        });
        /////////////////////////////////////////////////////////////////

        Log.d(TAG, "BussA = " + mBussA + ", BussB=" + mBussB);
        mBussA.method1A();
        mBussB.method1B();

        // decorView类型：androidx.appcompat.widget.ContentFrameLayout
        View decorView = findViewById(android.R.id.content);

        _layoutBinding = MainActivityLayoutBinding.inflate(getLayoutInflater());
        View rootView = _layoutBinding.getRoot();

        // setContentView之后就把当前我们定义的layout布局添加到了decorView上，这样decorView就有了一个children
        // 它的child就是ConstraintLayout，即我们在layout里定义的根视图 rootView：ConstraintLayout
        setContentView(rootView);

        Window activityWindow = getWindow();
        Log.d(TAG, "window:: getWindow()=== " + activityWindow);

        _layoutBinding.testTouchEvent.setOnClickListener(this);
        _layoutBinding.testFragmentEntry.setOnClickListener(this);
        _layoutBinding.testViper.setOnClickListener(this);

        // 首页注册EventPublisher事件
        testEventPublisher();

    }

    @Override public void onClick(View v) {

        if(v == _layoutBinding.testFragmentEntry) {

            // 进入到该页面后就发送 EventPublisher事件，看首页注册的地方能不能收到
            Log.d(TAG, "xl::::BaseEventPublisher.publish");
            BaseEventPublisher.getPublisher().publish("category_abc");

            startActivity(new Intent(this, TestFragmentEntryActivity.class));
        } else if (v ==  _layoutBinding.testTouchEvent) {
            //startActivity(new Intent(this, EnjoyTouchEventActivity.class));
            setResult(100, new Intent());
            finish();
        } else if (v ==  _layoutBinding.testViper) {
        }

    }

    /**
     * 直接通过 databinding xml文件link过来的，一般不建议这样写，可读性比较差
     * @param view
     */
    public void onDialogEntryClicked(View view) {

        // 测试Dialog的Window和Activity的Window是否是同一个对象：不是同一个对象

        // 测试Application的context.getSystemService和Activity的context.getSystemService是否是同一个对象：
        // 对于WINDOW_SERVICE和SEARCH_SERVICE来讲不是同一个对象，除了这两个之外其他的Service都是同一个对象，其他的Service
        // 最终都是调用到SystemServiceRegistry.getSystemService(this, name)这个静态方法来获取的，
        // 最终都是走到了SYSTEM_SERVICE_FETCHERS这个ArrayMap中根据传人的servicename来获取的

        // 测试Dialog的WindowManager和Activity的WindowManger是否是同一个对象：WindowManager对象是由传进来的Contxt决定的，如果传的是Application.context，那么会导致WindowManager不一样，最终导致crash

        // 测试Dialog如果传Application的Context会导致crash的原理
        Object windowServiceFromApplicationContextGet = getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        Object windowServiceFromActivityContextGet = getSystemService(Context.WINDOW_SERVICE);

        Log.d(TAG, "windowServiceFromApplicationContextGet ?= windowServiceFromActivityContextGet = " + (windowServiceFromApplicationContextGet == windowServiceFromActivityContextGet));

        Dialog dialog = new Dialog(getApplicationContext());
        Log.d(TAG, "Dialog.getWindow ?= activity.getWindow = " + (dialog.getWindow() == this.getWindow()));

        dialog.setTitle("弹框Title");
        TextView contentView = new TextView(this);
        contentView.setText("Dialog弹窗的内容！");
        dialog.setContentView(contentView);

        dialog.show();

    }


    public void onViewPager2Clicked(View view) {
        startActivity(new Intent(this, ViewPager2EntryActivity.class));
    }


    private void testEventPublisher() {
        Log.d(TAG, "xl::::testEventPublisher");
        BaseEventPublisher.getPublisher().subscribe("category_abc",
            new BaseEventPublisher.OnEventListener() {
                @Override public void onEvent(String category, Object event) {

                    Log.d(TAG, "xl::::收到事件：" + category + ", event=" + event);

                }
            });
    }


}