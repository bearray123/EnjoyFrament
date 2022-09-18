package xyz.rh.enjoyframent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    public static final String TAG = "FirstFragment";
    private TextView textView;
    private String mText;

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

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first, container, false);
        textView = rootView.findViewById(R.id.textview_first);
        textView.setText(textView.getText() + " :: " + mText);
        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "lifeCycle::onViewCreated() === " + hashCode());

    }

    /**
     * 在片段已与 Activity 关联时进行调用（Activity 传递到此方法内）。
     * @param context
     */
    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "lifeCycle::onAttach() === " + hashCode());
    }

    /**
     * 在取消片段与 Activity 的关联时进行调用。
     */
    @Override public void onDetach() {
        super.onDetach();
        Log.d(TAG, "lifeCycle::onDetach() === " + hashCode());
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
     * 在移除与片段关联的视图层次结构时进行调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "lifeCycle::onDestroyView() === " + hashCode());

    }

    @Override public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "lifeCycle::onDestroy() === " + hashCode());
    }

    public void updateContent(String text) {
        mText = text;
    }

}