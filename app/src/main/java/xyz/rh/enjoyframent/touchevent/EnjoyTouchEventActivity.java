package xyz.rh.enjoyframent.touchevent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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

   private FrameLayout mFrameLayout;
   private LinearLayout mLinearlayout;

   private RHButton rhButton;
   private RHView rhView;

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.touch_event_layout);

      mFrameLayout = findViewById(R.id.main_frame_layout);

      mFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(
          new ViewTreeObserver.OnGlobalLayoutListener() {
             @Override public void onGlobalLayout() {
                Log.d(TAG, "FrameLayout::onGlobalLayout ");

             }
          });

      mLinearlayout = findViewById(R.id.linear_layout);
      mLinearlayout.getViewTreeObserver().addOnGlobalLayoutListener(
          new ViewTreeObserver.OnGlobalLayoutListener() {
             @Override public void onGlobalLayout() {
                Log.d(TAG, "Linearlayout::onGlobalLayout ");

             }
          });

      rhButton = findViewById(R.id.rh_button);
      rhButton.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            mLinearlayout.addView(rhView);
            Log.d(TAG, "rhView:: has been added to mLinearlayout");
         }
      });

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

      rhView = new RHView(this);
      //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      //    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 50);

      rhView.setLayoutParams(layoutParams);
      rhView.measure(View.MeasureSpec.makeMeasureSpec(40, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(20, View.MeasureSpec.EXACTLY));
      rhView.layout(0,0, rhView.getMeasuredWidth(), rhView.getMeasuredHeight());
      rhView.setBackgroundColor(Color.GREEN);

      Log.d(TAG, "rhView:: width=" + rhView.getWidth() + ", rhView.Height=" + rhView.getHeight());




   }

   @Override public boolean dispatchTouchEvent(MotionEvent ev) {
      //Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
      return super.dispatchTouchEvent(ev);
   }
}
