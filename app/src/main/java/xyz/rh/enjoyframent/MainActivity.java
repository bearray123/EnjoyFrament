package xyz.rh.enjoyframent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.enjoy.ribs.VIPERActivity;
import xyz.rh.enjoyframent.databinding.MainActivityLayoutBinding;
import xyz.rh.enjoyframent.touchevent.EnjoyTouchEventActivity;
import xyz.rh.enjoyframent.touchevent.TestFragmentEntryActivity;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private MainActivityLayoutBinding _layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            startActivity(new Intent(this, EnjoyTouchEventActivity.class));
        } else if (v ==  _layoutBinding.testViper) {
            startActivity(new Intent(this, VIPERActivity.class));
        }

    }

}