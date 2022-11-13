package xyz.rh.enjoyframent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import xyz.rh.enjoyframent.R;
import xyz.rh.enjoyframent.fragment.view.VolumeView;

public class SecondFragment extends Fragment {

    public static final String TAG = "SecondFragment";

    private TextView textView;
    private String mText;

    private ViewGroup rootView;



    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "lifeCycle::onCreate() === " + hashCode());
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {

        Log.d(TAG, "lifeCycle::onCreateView() === " + hashCode());

        rootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_second, container, false);
        textView = rootView.findViewById(R.id.textview_second);
        textView.setText(textView.getText() + " :: " + mText);

        return rootView;

    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "lifeCycle::onAttach() === " + hashCode());
    }

    @Override public void onDetach() {
        super.onDetach();
        Log.d(TAG, "lifeCycle::onDetach() === " + hashCode());
    }

    private void addSubViewByCode() {
        View childView = new VolumeView(getContext());
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;  // 与父容器ConstraintLayout左边对齐
        layoutParams.topToBottom = textView.getId(); // 在textview的下边
        layoutParams.topMargin = 50;  //
        layoutParams.leftMargin = 50; //
        layoutParams.bottomMargin = 20; //
        childView.setLayoutParams(layoutParams);
        rootView.addView(childView, layoutParams);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 通过代码动态添加子View
        addSubViewByCode();

        Log.d(TAG, "lifeCycle::onViewCreated() === " + hashCode());

    }

    @Override public void onResume() {
        super.onResume();
        Log.d(TAG, "lifeCycle::onResume() === " + hashCode());
    }

    @Override public void onPause() {
        super.onPause();
        Log.d(TAG, "lifeCycle::onPause() === " + hashCode());
    }

    @Override public void onStop() {
        super.onStop();
        Log.d(TAG, "lifeCycle::onStop() === " + hashCode());
    }

    /**
     * onDestroyView() 顾名思义，只是销毁在fragment上绑定的view。该生命周期并不代表fragment被销毁
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "lifeCycle::onDestroyView() === " + hashCode());
    }

    /**
     * 这个才代表fragment被销毁
     */
    @Override public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "lifeCycle::onDestroy() === " + hashCode());
    }


    public void updateContent(String text) {
        mText = text;
    }
}