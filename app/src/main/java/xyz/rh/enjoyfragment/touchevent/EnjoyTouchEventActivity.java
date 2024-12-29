package xyz.rh.enjoyfragment.touchevent;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import xyz.rh.enjoyfragment.BaseActivity;
import xyz.rh.enjoyfragment.R;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class EnjoyTouchEventActivity extends BaseActivity {

   public static final String TAG = "EnjoyTouchEventActivity";

   private FrameLayout mFrameLayout;
   private LinearLayout mLinearlayout;

   private RHButton rhButton;

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
      mLinearlayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
         @Override
         public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
             int oldTop, int oldRight, int oldBottom) {
            String layoutData = "left=" + left + ",top=" + top + ",right=" + right + ",bottom=" + bottom;
            Log.d(TAG, "Linearlayout::onLayoutChange[布局变化] ----> " + layoutData);

         }
      });

      mLinearlayout.getViewTreeObserver().addOnPreDrawListener(
          new ViewTreeObserver.OnPreDrawListener() {
             @Override public boolean onPreDraw() {
                Log.d(TAG, "Linearlayout::onPreDraw");
                return true;
             }
          });

      new Handler().postDelayed(new Runnable() {
         @Override public void run() {
            mLinearlayout.addView(generateRHView());
         }
      }, 1000);

      rhButton = findViewById(R.id.rh_button);
      rhButton.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            mLinearlayout.addView(generateRHView());
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


   }

   private RHView generateRHView() {
      RHView rhView = new RHView(this);
      //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      //    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 150);

      rhView.setLayoutParams(layoutParams);
      rhView.measure(View.MeasureSpec.makeMeasureSpec(40, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(20, View.MeasureSpec.EXACTLY));
      rhView.layout(0,0, rhView.getMeasuredWidth(), rhView.getMeasuredHeight());
      rhView.setBackgroundColor(Color.GREEN);
      return rhView;
   }

   @Override public boolean dispatchTouchEvent(MotionEvent ev) {
      //Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
      return super.dispatchTouchEvent(ev);
   }
}
