package xyz.rh.enjoyframent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewStub;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.List;
import xyz.rh.common.BaseFragment;
import xyz.rh.enjoyframent.R;
import xyz.rh.enjoyframent.fragment.view.RHConstraintLayout;
import xyz.rh.enjoyframent.fragment.view.VolumeView;

public class SecondFragment extends BaseFragment {

    public static final String TAG = "SecondFragment";

    private TextView textView;
    private String mText;

    private ViewGroup rootView;

    private RHConstraintLayout constraintContainer;



    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_second, container, false);
        textView = rootView.findViewById(R.id.textview_second);


        constraintContainer = rootView.findViewById(R.id.rh_constraint_container);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        int currentFragmentIndex = fragmentList.indexOf(this);

        textView.setText(textView.getText() + " :: " + currentFragmentIndex);

        return rootView;

    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override public void onDetach() {
        super.onDetach();
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

    }

    @Override public void onResume() {
        super.onResume();

    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onStop() {
        super.onStop();
    }

    /**
     * onDestroyView() 顾名思义，只是销毁在fragment上绑定的view。该生命周期并不代表fragment被销毁
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 这个才代表fragment被销毁
     */
    @Override public void onDestroy() {
        super.onDestroy();
    }


    public void updateContent(String text) {
        mText = text;
    }
}