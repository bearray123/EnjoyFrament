package xyz.rh.enjoyfragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.rh.common.KotlinExtensionKt;
import xyz.rh.common.eventpublisher.BaseEventPublisher;
import xyz.rh.enjoyfragment.coroutine.TestCoroutineActivity;
import xyz.rh.enjoyfragment.databinding.MainActivityLayoutBinding;
import xyz.rh.enjoyfragment.di.test.BussA;
import xyz.rh.enjoyfragment.di.test.BussBFrom3rdParty;
import xyz.rh.enjoyfragment.di.test.DaggerBussComponent;
import xyz.rh.enjoyfragment.fragment.TestFragmentEntryActivity;
import xyz.rh.enjoyfragment.jsonparser.TestJsonParserActivity;
import xyz.rh.enjoyfragment.kotlin.KotlinMainActivity;
import xyz.rh.enjoyfragment.layoutparams.TestLayoutParamsActivity;
import xyz.rh.enjoyfragment.scroll.TestScrollActivity;
import xyz.rh.enjoyfragment.touchevent.EnjoyTouchEventActivity;
import xyz.rh.enjoyfragment.viewpager2.ViewPager2EntryActivity;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

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

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AudioManager.RINGER_MODE_CHANGED_ACTION)) {
                AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                final int ringerMode = audioManager.getRingerMode();
                switch (ringerMode) {
                    case AudioManager.RINGER_MODE_NORMAL:
                        Log.d("testabc", "正常模式~~");
                        //normal
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        Log.d("testabc", "震动模式~~");
                        //vibrate
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        Log.d("testabc", "静音模式~~");
                        //silent
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity::onCreate  ---> this = " + this + " ,savedInstanceState = " + savedInstanceState);
        registerReceiver(mReceiver, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
        Log.d("","getApplication == " + getApplication() + ",,,,,getApplication().getApplicationContext()" + getApplication().getApplicationContext());
        super.onCreate(savedInstanceState);
        KotlinExtensionKt.setApplication(getApplication().getApplicationContext());
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

        // 测试 activity中任何一个view的RootView是什么：
        // Activity中任何一个view的rootView是 DecorView，自己的layout文件inflate出来然后setContentView的 不是DecorView。
        View rootViewForFragmentEntry = _layoutBinding.testFragmentEntry.getRootView(); // DecorView
        View rootViewForTouchEvent = _layoutBinding.testTouchEvent.getRootView(); // DecorView
        Log.d(TAG, "test rootView===== rootViewForFragmentEntry=" + rootViewForFragmentEntry + ", rootViewForTouchEvent=" + rootViewForTouchEvent + "######## rootViewForMyLayout=" + rootView);

        rootViewForFragmentEntry.post(new Runnable() { // 这里必须post，如果不post的话获取到的parent是null，因为在onCreate里面，还没走view的组装绘制流程。
            @Override public void run() {
                // testFragmentEntry获取到的parent是： ConstraintLayout
                // decorView.getParent 是  android.view.ViewRootImpl
                // 总结：普通子View的Parent获取到的是我们布局中定义的父View，DecorView已经是Window中最顶层View了，他的父View是ViewRootImpl
                Log.d(TAG, "test getParent ==== testFragmentEntry.getParent" + _layoutBinding.testFragmentEntry.getParent() + " ##### rootViewForFragmentEntry.getParent =" + rootViewForFragmentEntry.getParent());
            }
        });

        Window activityWindow = getWindow();
        Log.d(TAG, "window:: getWindow()=== " + activityWindow);

        _layoutBinding.testKotlin.setOnClickListener(this);
        _layoutBinding.testTouchEvent.setOnClickListener(this);
        _layoutBinding.testFragmentEntry.setOnClickListener(this);
        _layoutBinding.mySkipBtn.setOnClickListener(this);
        //_layoutBinding.superAppShieldIcon.setOnClickListener(this);

        _layoutBinding.testViper.setOnClickListener(this);

        _layoutBinding.testLayoutparams.setOnClickListener(this);
        _layoutBinding.testJsonParser.setOnClickListener(this);
        _layoutBinding.openDidiApp.setOnClickListener(this);

        _layoutBinding.testScroll.setOnClickListener(this);

        _layoutBinding.openCoroutineTestPage.setOnClickListener(this);

        // 首页注册EventPublisher事件
        testEventPublisher();

        Uri uri = Uri.parse("onetravel://dache_anycar/confirm/cccc");
        Log.d(TAG, "eeeeeeee======22222 uri.getAuthority()====" + uri.getAuthority()); //dache_anycar
        Log.d(TAG, "eeeeeeee======22222 uri.getHost()====" + uri.getHost()); //dache_anycar
        Log.d(TAG, "eeeeeeee======22222 uri.getPath()===" + uri.getPath()); ///confirm/cccc
        Log.d(TAG, "eeeeeeee======22222 uri.getScheme()===" + uri.getScheme()); ///confirm/cccc

    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override public void onClick(View v) {

        if (v == _layoutBinding.testKotlin) {
            startActivity(new Intent(this, KotlinMainActivity.class));
        } else if(v == _layoutBinding.testFragmentEntry) {
            // 进入到该页面后就发送 EventPublisher事件，看首页注册的地方能不能收到
            Log.d(TAG, "xl::::BaseEventPublisher.publish");
            BaseEventPublisher.getPublisher().publish("category_abc");

            startActivity(new Intent(this, TestFragmentEntryActivity.class));
        } else if (v ==  _layoutBinding.testTouchEvent) {
            startActivity(new Intent(this, EnjoyTouchEventActivity.class));
        } else if (v ==  _layoutBinding.testViper) {
        } else if (v ==  _layoutBinding.testLayoutparams) {
            startActivity(new Intent(this, TestLayoutParamsActivity.class));
        }
        else if (v == _layoutBinding.mySkipBtn) {
            startActivity(new Intent(this, TestLayoutParamsActivity.class));
        }
        else if (v == _layoutBinding.testJsonParser) {
            startActivity(new Intent(this, TestJsonParserActivity.class));
        }
        else if (v == _layoutBinding.openDidiApp) { // DeepLink跳转到滴滴出行
            Intent intent = new Intent();
            // 以下setAction、addCategory 不加也行
            //intent.setAction("android.intent.action.VIEW");
            //intent.addCategory("android.intent.category.DEFAULT");
            //intent.addCategory("android.intent.category.BROWSABLE");
            Uri work = Uri.parse("OneTravel://dache_anycar/entrance");
            intent.setData(work);
            // 这种写法是不work的，无法实现deeplink跳转，生成的对象是Uri$HierarchicalUri，对应的scheme是UrlEncode之后的：/OneTravel%3A%2F%2Fdache_anycar%2Fentrance
            //Uri notwork = new Uri.Builder().appendPath("OneTravel://dache_anycar/entrance").build();
            //intent.setData(new Uri.Builder().appendPath("OneTravel://dache_anycar/entrance").build());
            startActivity(intent);
        }
        else if (v == _layoutBinding.testScroll) {
            startActivity(new Intent(this, TestScrollActivity.class));
        }
        else if (v == _layoutBinding.openCoroutineTestPage) {
            startActivity(new Intent(this, TestCoroutineActivity.class));
        }

    }

    @Override protected void onPause() {
        Log.d(TAG, "xl::::onPause");
        super.onPause();
    }

    @Override protected void onStop() {
        Log.d(TAG, "xl::::onStop");
        super.onStop();
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

        Log.d(TAG, "windowServiceFromApplicationContextGet ?= windowServiceFromActivityContextGet = " + (windowServiceFromApplicationContextGet == windowServiceFromActivityContextGet)); // false

        //Dialog dialog = new Dialog(getApplicationContext()); // Crash
        Dialog dialog = new Dialog(this);
        Log.d(TAG, "Dialog.getWindow ?= activity.getWindow = " + (dialog.getWindow() == this.getWindow())); // false

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