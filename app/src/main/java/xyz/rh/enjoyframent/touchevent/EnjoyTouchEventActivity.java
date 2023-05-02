package xyz.rh.enjoyframent.touchevent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import xyz.rh.enjoyframent.R;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class EnjoyTouchEventActivity extends AppCompatActivity {

   public static final String TAG = "EnjoyTouchEventActivity";

   private FrameLayout mFramelayout;
   private LinearLayout mLinearlayout;

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.touch_event_layout);

      mFramelayout = findViewById(R.id.main_frame_layout);
      mLinearlayout = findViewById(R.id.linear_layout);
      //mLinearlayout.setClickable(false);
      //mLinearlayout.setOnTouchListener(new View.OnTouchListener() {
      //   @Override public boolean onTouch(View v, MotionEvent event) {
      //      return false;
      //   }
      //});




      //mFramelayout.setOnTouchListener(new View.OnTouchListener() {
      //   @Override public boolean onTouch(View v, MotionEvent event) {
      //      return false;
      //   }
      //});
      //
      //mLinearlayout.setOnTouchListener(new View.OnTouchListener() {
      //   @Override public boolean onTouch(View v, MotionEvent event) {
      //      return false;
      //   }
      //});

      RHView rhView = new RHView(this);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      rhView.setLayoutParams(layoutParams);
      rhView.measure(View.MeasureSpec.makeMeasureSpec(20, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(40, View.MeasureSpec.EXACTLY));
      rhView.layout(0,0, rhView.getMeasuredWidth(), rhView.getMeasuredHeight());

      Log.d(TAG, "rhView:: width=" + rhView.getWidth() + ", rhView.Height=" + rhView.getHeight());



   }

   @Override public boolean dispatchTouchEvent(MotionEvent ev) {
      //Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
      return super.dispatchTouchEvent(ev);
   }
}
