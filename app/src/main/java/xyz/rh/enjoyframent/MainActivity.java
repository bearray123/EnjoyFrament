package xyz.rh.enjoyframent;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.rh.common.KotlinExtensionKt;
import xyz.rh.common.eventpublisher.BaseEventPublisher;
import xyz.rh.enjoyframent.databinding.MainActivityLayoutBinding;
import xyz.rh.enjoyframent.di.test.BussA;
import xyz.rh.enjoyframent.di.test.BussBFrom3rdParty;
import xyz.rh.enjoyframent.di.test.DaggerBussComponent;
import xyz.rh.enjoyframent.fragment.TestFragmentEntryActivity;
import xyz.rh.enjoyframent.jsonparser.TestJsonParserActivity;
import xyz.rh.enjoyframent.layoutparams.TestLayoutParamsActivity;
import xyz.rh.enjoyframent.scroll.TestScrollActivity;
import xyz.rh.enjoyframent.touchevent.EnjoyTouchEventActivity;
import xyz.rh.enjoyframent.viewpager2.ViewPager2EntryActivity;

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

        _layoutBinding.testTouchEvent.setOnClickListener(this);
        _layoutBinding.testFragmentEntry.setOnClickListener(this);
        _layoutBinding.mySkipBtn.setOnClickListener(this);
        //_layoutBinding.superAppShieldIcon.setOnClickListener(this);

        _layoutBinding.testViper.setOnClickListener(this);

        _layoutBinding.testLayoutparams.setOnClickListener(this);
        _layoutBinding.testJsonParser.setOnClickListener(this);
        _layoutBinding.openDidiApp.setOnClickListener(this);

        _layoutBinding.testScroll.setOnClickListener(this);


        // 首页注册EventPublisher事件
        testEventPublisher();

        //try {
        //    GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {});
        //    _layoutBinding.testTouchEvent.setBackground(drawable);
        //} catch (Throwable e) {
        //    Log.d(TAG, "eeeeeeee======111111 " + activityWindow);
        //    e.printStackTrace();
        //}

        Uri uri = Uri.parse("onetravel://dache_anycar/confirm/cccc");



        Log.d(TAG, "eeeeeeee======22222 uri.getAuthority()====" + uri.getAuthority()); //dache_anycar
        Log.d(TAG, "eeeeeeee======22222 uri.getHost()====" + uri.getHost()); //dache_anycar
        Log.d(TAG, "eeeeeeee======22222 uri.getPath()===" + uri.getPath()); ///confirm/cccc
        Log.d(TAG, "eeeeeeee======22222 uri.getScheme()===" + uri.getScheme()); ///confirm/cccc

        //String originStr = "{\"sids\": [{\"k1\": 1}, {\"k2\": 2}, {\"k2\": 2}, {\"k2\": 2}]}";
        String originStr = "{\"sids\": {\"k1\": 1, \"k2\": 2, \"k3\": 4, \"k4\": \"5\"}}";
        //{
        //    "sids": {
        //        "k1": 1,
        //        "k2": 2,
        //        "k2": 2,
        //        "k2": 2
        //    }
        //}

        //try {
        //    //JSONObject JSO = new JSONObject(json);
        //    //JSONObject jsonObject = JSO.getJSONObject("sids");
        //    //JSONArray jsonArray = JSO.optJSONArray("sids");
        //    //jsonArray.
        //
        //    Config config = JSON.parseObject(originStr, Config.class);
        //
        //    Map jsonObject = JSON.parseObject(config.sids, Map.class); // 得到的是一个HashMap
        //    //Map<String, String> transMap = (Map<String, String>)JSON.parse(originStr);
        //
        //    Log.d(TAG, "eeeeeeee======" + jsonObject); ///confirm/cccc
        //
        //    if (jsonObject.containsKey("k2")) {
        //        jsonObject.keySet();
        //    }
        //
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        //try {
        //    new Thread() {
        //
        //        @Override public void run() {
        //            while (true) {
        //                try {
        //                    Thread.sleep(2000);
        //                }catch (Exception e) {
        //
        //                }
        //                throw new RuntimeException("xl1000");
        //            }
        //        }
        //    }.start();
        //} catch (Exception E) {
        //    E.printStackTrace();
        //}

        JSONObject JSO = null;
        try {
            JSO = new JSONObject(data111);
            JSONArray jsonArray = JSO.getJSONArray("multi_require_product");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //JSONObject jsonObject = JSO.getJSONObject("sids");
        //    JSONArray jsonArray = JSO.optJSONArray("sids");

        //try {
        //    JSO = new JSONObject("{\n"
        //        + "\t\"errno\": \"0\",\n"
        //        + "\t\"errmsg\": \"SUCCESS\",\n"
        //        + "\t\"data\": \"OKxjFuFTzRtqRroGLyrk6YZW4WlOW2oz1QEEtyrMm7SSZHrjzGEVbsGF4Ei6+i14vVb34DdSQdjDojG6rmk3Ea4YpwY8OjtZfmzk0IIii4auS3rLBot9Jcht8cAnboDzwIgeVwtz2EIM7TkBmC7WUw==\"\n"
        //        + "}");
        //
        //    // {"errno": "0","errmsg": "SUCCESS","username": {"key1":"yhyhyhyhh"}}
        //    JSO = new JSONObject("{\"errno\": \"0\",\"errmsg\": \"SUCCESS\",\"username\": \"yhyhyhyhh\"}");
        //    JSONObject data = JSO.optJSONObject("username");
        //    //String data = JSO.optString("username");
        //
        //    if (JSO.has("data")) {
        //        Log.d("", "HAS DATA ");
        //    }
        //
        //    Log.d("", "data === " + data.toString());
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}


    }

    public String data111 = "{\"multi_require_product\": [\n"
        + "        {\n"
        + "            \"estimate_id\": \"fe994b84f228258b52b6b312c8698283\",\n"
        + "            \"product_id\": 3,\n"
        + "            \"business_id\": 260,\n"
        + "            \"combo_type\": 0,\n"
        + "            \"require_level\": 600,\n"
        + "            \"level_type\": 0,\n"
        + "            \"combo_id\": 0,\n"
        + "            \"route_type\": 0,\n"
        + "            \"is_special_price\": 0,\n"
        + "            \"count_price_type\": 0,\n"
        + "            \"pay_type\": \"2\",\n"
        + "            \"product_category\": 1\n"
        + "        },\n"
        + "        {\n"
        + "            \"estimate_id\": \"5e9df2595d9f2ed8c10b2822b0821cc1\",\n"
        + "            \"product_id\": 3,\n"
        + "            \"business_id\": 260,\n"
        + "            \"combo_type\": 314,\n"
        + "            \"require_level\": 600,\n"
        + "            \"level_type\": 0,\n"
        + "            \"combo_id\": 0,\n"
        + "            \"route_type\": 0,\n"
        + "            \"is_special_price\": 0,\n"
        + "            \"count_price_type\": 0,\n"
        + "            \"pay_type\": \"2\",\n"
        + "            \"product_category\": 61\n"
        + "        }\n"
        + "    ]\n"
        + "}";


    public static class Config {
        public String sids;

        public void setSids(String sids) {
            this.sids = sids;
        }

        //public String getSids() {
        //    return sids;
        //}
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override public void onClick(View v) {

        if(v == _layoutBinding.testFragmentEntry) {

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
        //else if (v == _layoutBinding.superAppShieldIcon) {
        //    startActivity(new Intent(this, TestLayoutParamsActivity.class));
        //}
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