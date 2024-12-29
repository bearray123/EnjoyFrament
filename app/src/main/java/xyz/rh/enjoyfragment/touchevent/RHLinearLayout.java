package xyz.rh.enjoyfragment.touchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/**
 * Created by xionglei01@baidu.com on 2022/9/21.
 */
public class RHLinearLayout extends LinearLayout {

    public static final String TAG = "RHLinearLayout";


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

    // region 绘制三部曲

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "draw lifecycle:: onMeasure 测量-> RHLinearLayout");
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "draw lifecycle:: onLayout 布局-> RHLinearLayout");
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "draw lifecycle:: onDraw 绘制-> RHLinearLayout");
    }
    // endregion


    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Event::: dispatchTouchEvent(), touchEvent == " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Event::: onInterceptTouchEvent(), touchEvent == " + ev.getAction());
        //return true; // 消费当前事件，不再往子view传递
        return super.onInterceptTouchEvent(ev);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Event::: onTouchEvent(), touchEvent == " + event.getAction());
        //return super.onTouchEvent(event);
        return true;
    }


}
