package xyz.rh.enjoyframent.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;
import xyz.rh.common.BaseFragment;
import xyz.rh.enjoyframent.R;
import xyz.rh.enjoyframent.dialog.RHDialogFragment;

import static xyz.rh.enjoyframent.Constants.GLOBAL_BACK_STACK_NAME;

public class FirstFragment extends BaseFragment {

    public static final String TAG = "FirstFragment";
    private TextView textView;
    private String mText;
    private Button mShowDialogBtn;
    private Button mStartSubFragment;

    private ActivityResultLauncher launcher = null;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActivity().registerForActivityResult()
        ActivityResultContract activityResultContract = new ActivityResultContracts.StartActivityForResult();
        launcher = registerForActivityResult(/*new ActivityResultContract<Object, Object>() {
            @NonNull @Override
            public Intent createIntent(@NonNull Context context, Object o) {
                Intent intent = new Intent(FirstFragment.this.getActivity(), MainActivity.class);
                return intent;
            }

            @Override public Object parseResult(int i, @Nullable Intent intent) {
                Log.d(TAG, ":::::parseResult i " + i + ", intent=" + intent);
                return null;
            }
        }*/
            activityResultContract, new ActivityResultCallback<ActivityResult>() {
            @Override public void onActivityResult(ActivityResult result) {
                Log.d(TAG, "::::: onActivityResult  result =" + result);
            }
        });

        //getActivity().startactivityfor

        launcher.unregister();




    }

    public void startAct(ActivityResultCallback resultCallback) {
        launcher.launch(null);
    }



    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);

        Fragment parent = getParentFragment();
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first, container, false);
        Log.w(TAG, "XL::: FistFragment  RootView === " + rootView + ", parentFragment == " + parent + this);

        textView = rootView.findViewById(R.id.textview_first);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        int currentFragmentIndex = fragmentList.indexOf(this);

        textView.setText(textView.getText() + " :: " + currentFragmentIndex + 1);  // index从0开始的


        Log.d(TAG, " textView.getRootView() ?= rootView :: " + (textView.getRootView() == rootView));

        mShowDialogBtn = rootView.findViewById(R.id.show_dialog_btn);
        mShowDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                // 方式一：采用DialogFragment来实现弹窗
                RHDialogFragment dialogFragment = RHDialogFragment.newInstance("","");
                dialogFragment.setBgColor(Color.GREEN);
                dialogFragment.show(getChildFragmentManager(), "");

                // 方式二：采用传统的AlertDialog来实现弹窗
                //showAlertDialog();

            }
        });

        mStartSubFragment = rootView.findViewById(R.id.start_sub_fragment);
        mStartSubFragment.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                SecondFragment subFragment = new SecondFragment();
                subFragment.updateContent("作为子Fragment添加到FirstFragment里面");

                // 验证getChildFragmentManager的使用：
                // 在使用getChildFragmentManager去启动和管理一个子fragment时，整个视图层级都是处于当前这个Fragment下的
                // 对应的container也必须是当前Fragment里的ViewGroup，如果取Activity层里面的container会crash报错：
                // java.lang.IllegalArgumentException: No view found for id 0x7f0800d1 (xyz.rh.enjoyframent:id/fragment_container) for fragment SecondFragment
                // crash的原因就是fragment_container其实不在当前这个Fragment里
                //transaction.replace(R.id.fragment_container, subFragment);

                //transaction.replace(R.id.sub_fragment_container, subFragment);
                //transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
                //transaction.commit();

                launcher.launch(null);


            }

        });

        Log.w(TAG, "XL::: FistFragment  textView.getRootView() === " + textView.getRootView());

        return rootView;

    }

    private void showAlertDialog() {
        ///  这里采用传统的dialog弹窗打log是为了确认传统dialog的window跟当前界面（activity/fragment）的window是不同的对象
        View dialogRootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_layout, null);
        AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());
        builder.setView(dialogRootView);
        AlertDialog dialog = builder.create();
        dialog.setTitle("oH，弹窗标题");
        //dialog.setContentView(dialogRootView);
        View jumpView = dialogRootView.findViewById(R.id.dialog_ck_btn);
        jumpView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                //transaction.replace(R.id.fragment_container, new SecondFragment());
                transaction.replace(R.id.global_dialog_container, ThirdFragment.Companion.newInstance());
                transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
                transaction.commit();
            }
        });

        //dialog.setMessage("这是用传统AlertDialog的弹窗: dialog.getOwnerActivity=" + dialog.getOwnerActivity());
        //dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "点击我弹一个DialogFragment弹窗",
        //    new DialogInterface.OnClickListener() {
        //        @Override public void onClick(DialogInterface dialog, int which) {
        //
        //            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //            //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //            //transaction.replace(R.id.fragment_container, new SecondFragment());
        //            transaction.replace(R.id.global_dialog_container, ThirdFragment.Companion.newInstance());
        //            transaction.addToBackStack(GLOBAL_BACK_STACK_NAME);
        //            transaction.commit();
        //
        //        }
        //    });
        dialog.show();
        Log.w(TAG, "window:: === AlertDialog.getWindow" + dialog.getWindow());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * 在片段已与 Activity 关联时进行调用（Activity 传递到此方法内）。
     * @param context
     */
    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * 在取消片段与 Activity 的关联时进行调用。
     */
    @Override public void onDetach() {
        super.onDetach();
    }

    @Override public void onResume() {
        super.onResume();
        Log.d(TAG, "window:: getActivity().getWindow() = " + getActivity().getWindow());
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onStop() {
        super.onStop();
    }

    /**
     * 在移除与片段关联的视图层次结构时进行调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    public void updateContent(String text) {
        mText = text;
    }

    static class MyActivityResultLauncher extends ActivityResultLauncher {

        @Override public void launch(Object input, @Nullable ActivityOptionsCompat options) {

        }

        @Override public void unregister() {

        }

        @NonNull @Override public ActivityResultContract getContract() {
            return null;
        }
    }

}