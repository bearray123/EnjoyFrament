package xyz.rh.enjoyframent.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by xionglei01@baidu.com on 2022/9/22.
 */
public class RHBotton extends androidx.appcompat.widget.AppCompatButton {

   public static final String TAG = "RHBotton";

   public RHBotton(Context context) {
      super(context);
   }

   public RHBotton(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public RHBotton(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }


   @Override public boolean dispatchTouchEvent(MotionEvent ev) {
      Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
      return super.dispatchTouchEvent(ev);
   }

   @Override public boolean onTouchEvent(MotionEvent event) {
      Log.d(TAG, "Event::: onTouchEvent(), touchEvent == " + event.getAction());
      return super.onTouchEvent(event);
   }


}
