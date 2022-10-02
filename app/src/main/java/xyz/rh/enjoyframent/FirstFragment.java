package xyz.rh.enjoyframent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import xyz.rh.enjoyframent.dialog.DialogFragment;

import static xyz.rh.enjoyframent.Constants.GLOBAL_BACK_STACK_NAME;

public class FirstFragment extends Fragment {

    public static final String TAG = "FirstFragment";
    private TextView textView;
    private String mText;
    private Button mShowDialogBtn;

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

        Fragment parent = getParentFragment();
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first, container, false);
        Log.w(TAG, "XL::: FistFragment  RootView === " + rootView + ", parentFragment == " + parent + this);


        textView = rootView.findViewById(R.id.textview_first);
        textView.setText(textView.getText() + " :: " + mText);

        mShowDialogBtn = rootView.findViewById(R.id.show_dialog_btn);
        mShowDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                // DialogFragment.show(fragmentManager, tag) 这个是不会添加到回退栈的
                //dialogFragment.setCancelable(false); //

                //DialogFragment dialogFragment = DialogFragment.newInstance("","");
                //dialogFragment.setBgColor(Color.GREEN);
                //dialogFragment.show(getChildFragmentManager(), "");


                ///  这里采用传统的dialog弹窗打log是为了确认传统dialog的window跟当前界面（activity/fragment）的window是不同的对象
                AlertDialog dialog = new  AlertDialog.Builder(getContext()).create();
                dialog.setTitle("oH，弹窗标题");
                dialog.setMessage("这是用传统AlertDialog的弹窗: dialog.getOwnerActivity=" + dialog.getOwnerActivity());
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "点击我弹一个DialogFragment弹窗",
                    new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new SecondFragment());
                            transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
                            transaction.commit();

                        }
                    });
                dialog.show();
                Log.w(TAG, "window:: === AlertDialog.getWindow" + dialog.getWindow());

            }
        });
        Log.w(TAG, "XL::: FistFragment  textView.getRootView() === " + textView.getRootView());

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
        Log.d(TAG, "window:: getActivity().getWindow() = " + getActivity().getWindow());
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