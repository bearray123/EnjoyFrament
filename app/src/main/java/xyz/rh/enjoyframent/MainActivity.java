package xyz.rh.enjoyframent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import javax.inject.Inject;
import xyz.rh.enjoyframent.databinding.MainActivityLayoutBinding;
import xyz.rh.enjoyframent.di.test.BussA;
import xyz.rh.enjoyframent.di.test.BussBFrom3rdParty;
import xyz.rh.enjoyframent.di.test.DaggerBussComponent;
import xyz.rh.enjoyframent.touchevent.EnjoyTouchEventActivity;

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

    }

    @Override public void onClick(View v) {

        if(v == _layoutBinding.testFragmentEntry) {
            startActivity(new Intent(this, TestFragmentEntryActivity.class));
        } else if (v ==  _layoutBinding.testTouchEvent) {
            //startActivity(new Intent(this, EnjoyTouchEventActivity.class));
            setResult(100, new Intent());
            finish();
        } else if (v ==  _layoutBinding.testViper) {
        }

    }

}