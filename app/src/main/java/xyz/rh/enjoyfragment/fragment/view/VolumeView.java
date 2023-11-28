package xyz.rh.enjoyfragment.fragment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import xyz.rh.enjoyfragment.R;

/**
 * 动态音量显示条
 * Created by bearray123@sina.com on 2022/5/29.
 */
public class VolumeView extends View {

    private static final String TAG = "VolumeView";
    private static final int MAX_VOLUME_PERCENT = 10; //最大音量，10代表100% 或100db

    private int volumePercent;

    private int viewWidth;
    private int viewHeight;

    public int defaultWidth = 200;
    public int defaultHeight = 200;
    @ColorInt
    private int occupiedColor = Color.GREEN;
    @ColorInt
    private int unoccupiedColor = Color.LTGRAY;

    private float cornerRadius = 0; // 圆角率

    private final Paint paint = new Paint();

    public VolumeView(Context context) {
        super(context);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VolumeView);
        occupiedColor = typedArray.getColor(R.styleable.VolumeView_occupied_color, occupiedColor);
        unoccupiedColor = typedArray.getColor(R.styleable.VolumeView_unoccupied_color, unoccupiedColor);
        cornerRadius = typedArray.getFloat(R.styleable.VolumeView_radius, cornerRadius);
        typedArray.recycle(); // 使用完后一定要回收
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ///// 放到这个构造器无效，获取不到
        //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.volume_view_sty);
        //occupiedColor = typedArray.getColor(R.styleable.volume_view_sty_occupied_color, occupiedColor);
        //unoccupiedColor = typedArray.getColor(R.styleable.volume_view_sty_unoccupied_color, unoccupiedColor);
        //cornerRadius = typedArray.getFloat(R.styleable.volume_view_sty_radius, cornerRadius);
        //typedArray.recycle(); // 使用完后一定要回收
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * widthMeasureSpec是父layout对自身的测量规范，这个值在父布局里通过getChildMeasureSpec拿到的，在计算这个值时已经把子view的layoutParams.left算进去了
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSizeFromSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSizeFromSpec = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY: //固定值
                viewWidth = widthSizeFromSpec;
                break;
            case MeasureSpec.AT_MOST: //wrap_content
                viewWidth = Math.min(defaultWidth,widthSizeFromSpec);
                break;
            //case MeasureSpec.UNSPECIFIED:
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                viewHeight = heightSizeFromSpec;
                break;
            case MeasureSpec.AT_MOST:
                viewHeight = Math.min(defaultHeight, heightSizeFromSpec);
                break;
            //case MeasureSpec.UNSPECIFIED:
        }

        Log.d(TAG, "onMeasure:: w = " + viewWidth + ", h=" + viewHeight);
        setMeasuredDimension(viewWidth,viewHeight);

    }

    @Override protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.d(TAG, "onDraw() :: getMeasuredWidth = " + getMeasuredWidth() + ", getMeasuredHeight = " + getMeasuredHeight());
        canvas.save();
        // 画笔宽度
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 画笔颜色
        paint.setColor(occupiedColor);

        RectF preRectF = null;
        //canvas.drawRoundRect(originRectF,radius, radius, paint);

        // 画深色部分，当前音量的占比
        for (int i=0; i<volumePercent; i++) {
            RectF current = nextRectF(preRectF);
            Log.d(TAG, "entity volume :: rectF == " +current);
            canvas.drawRoundRect(current, cornerRadius, cornerRadius, paint);
            preRectF = current;
        }

        // 画剩下浅色部分
        for(int i= volumePercent; i<MAX_VOLUME_PERCENT; i++) {
            RectF current = nextRectF(preRectF);
            Log.d(TAG, "stroke volume :: rectF == " +current);
            paint.setColor(unoccupiedColor);
            canvas.drawRoundRect(current, cornerRadius, cornerRadius, paint);
            preRectF =current;
        }

        canvas.restore();

    }

    private RectF nextRectF(RectF preRectF) {
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        float h_first = viewHeight - viewHeight*0.1f*9;
        float h_interval = viewHeight*0.1f;
        float clearance = viewWidth/39f;
        float w = clearance*3;

        if (preRectF == null) { // 第一个
            // 先不管padding
            // 下面注释的部分是计算x y的思路，x代表一个音量条的宽度，y代表音量条之间的间距，通过这两个变量和对应的方程计算出x和y
            // 假设固定10个音量条，那么就有9个间距，我们约定音量条的宽度x是间距的3倍
            //10*x + 9*y = viewWidth
            //    x/y=3
            //        x = 3y
            //10*3y + 9y = viewWidth
            //39y = viewWidth
            //y = viewWidth/39
            return new RectF(0, 0, w, h_first);
        }

        // 第二个开始
        float left = preRectF.right + clearance;
        float right = left + w;
        return new RectF(left,0,right,preRectF.height() + h_interval);
    }


    public void refresh(int percent) {
        Log.d(TAG, "refresh: " + percent);
        // 传进来的音量值必须<=10
        assert percent <= MAX_VOLUME_PERCENT;
        volumePercent = percent;
        postInvalidate();
        invalidate();
    }

    @Override protected void onAttachedToWindow() {
        Log.d(TAG, "lifeCycle::onAttachedToWindow()===");
        super.onAttachedToWindow();
    }

    @Override protected void onDetachedFromWindow() {
        Log.d(TAG, "lifeCycle::onDetachedFromWindow()$$$");
        super.onDetachedFromWindow();
    }

}
