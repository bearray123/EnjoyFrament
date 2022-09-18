package xyz.rh.enjoyframent;

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

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first, null);
        textView = rootView.findViewById(R.id.textview_first);
        textView.setText(textView.getText() + " :: " + mText);
        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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