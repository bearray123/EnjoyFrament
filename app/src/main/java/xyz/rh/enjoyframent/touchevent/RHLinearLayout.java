package xyz.rh.enjoyframent.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class RHLinearLayout extends LinearLayout {

    public static final String TAG = "MyLinearLayout";


    public RHLinearLayout(Context context) {
        super(context);
    }

    public RHLinearLayout(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RHLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RHLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Event::: onInterceptTouchEvent(), touchEvent == " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Event::: onTouchEvent(), touchEvent == " + event.getAction());
        return super.onTouchEvent(event);
    }


}
