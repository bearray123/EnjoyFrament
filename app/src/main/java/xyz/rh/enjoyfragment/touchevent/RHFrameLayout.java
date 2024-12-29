package xyz.rh.enjoyfragment.touchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class RHFrameLayout extends FrameLayout {

    public static final String TAG = "RHFrameLayout";

    public RHFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RHFrameLayout(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RHFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // region start 绘制三部曲
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "draw lifecycle:: onMeasure 测量-> RHFrameLayout");
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "draw lifecycle:: onLayout 布局-> RHFrameLayout");
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "draw lifecycle:: onDraw 绘制-> RHFrameLayout");
    }
    // endregion

    public RHFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
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
