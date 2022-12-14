package xyz.rh.enjoyframent.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        textView.setText(textView.getText() + " :: " + currentFragmentIndex + 1);  // index???0?????????


        Log.d(TAG, " textView.getRootView() ?= rootView :: " + (textView.getRootView() == rootView));

        mShowDialogBtn = rootView.findViewById(R.id.show_dialog_btn);
        mShowDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                // DialogFragment.show(fragmentManager, tag) ????????????????????????????????????
                //dialogFragment.setCancelable(false); //

                //DialogFragment dialogFragment = DialogFragment.newInstance("","");
                //dialogFragment.setBgColor(Color.GREEN);
                //dialogFragment.show(getChildFragmentManager(), "");


                ///  ?????????????????????dialog?????????log?????????????????????dialog???window??????????????????activity/fragment??????window??????????????????
                View dialogRootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_layout, null);
                AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());
                builder.setView(dialogRootView);
                AlertDialog dialog = builder.create();
                dialog.setTitle("oH???????????????");
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

                //dialog.setMessage("???????????????AlertDialog?????????: dialog.getOwnerActivity=" + dialog.getOwnerActivity());
                //dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "??????????????????DialogFragment??????",
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
        });

        mStartSubFragment = rootView.findViewById(R.id.start_sub_fragment);
        mStartSubFragment.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                SecondFragment subFragment = new SecondFragment();
                subFragment.updateContent("?????????Fragment?????????FirstFragment??????");

                // ??????getChildFragmentManager????????????
                // ?????????getChildFragmentManager???????????????????????????fragment????????????????????????????????????????????????Fragment??????
                // ?????????container??????????????????Fragment??????ViewGroup????????????Activity????????????container???crash?????????
                // java.lang.IllegalArgumentException: No view found for id 0x7f0800d1 (xyz.rh.enjoyframent:id/fragment_container) for fragment SecondFragment
                // crash???????????????fragment_container????????????????????????Fragment???
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * ??????????????? Activity ????????????????????????Activity ???????????????????????????
     * @param context
     */
    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * ?????????????????? Activity ???????????????????????????
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
     * ???????????????????????????????????????????????????????????????
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