package xyz.rh.enjoyframent.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.List;
import xyz.rh.common.BaseFragment;
import xyz.rh.enjoyframent.R;
import xyz.rh.enjoyframent.dialog.RHDialogFragment;

import static xyz.rh.enjoyframent.Constants.GLOBAL_BACK_STACK_NAME;

public class FirstFragment extends BaseFragment {

    public static final String TAG = "FirstFragment";
    private TextView textView;
    private ImageView firstImg;
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
        firstImg = rootView.findViewById(R.id.first_image);

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
                // 这里在show时：如果传入的fragmentManager是getChildFragmentManager，则fragment跳走后当前fragmentDialog会正常被盖住
                // 如果传入的fragmentManager是activity.getSupportFragmentManager，在页面跳走后当前fragmentDialog不会消失，会覆盖到目标fragment之上
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

        //////////////////////////////////////////////////////////////////
        // 在fragment中测试Glide图片加载，如果fragment已经销毁 或 detach了，使用Glide是啥样的效果
        // 敌法师图片:
        String antiImgUrl = "https://steamcdn-a.akamaihd.net/apps/dota2/images/dota_react/heroes/antimage.png";
        // 百度上巩俐性感照片： 这个图片大小是360KB，非常适合用来模拟charles慢网速图片加载的case，需要把charles throttle配置下： Download:200, upload:100  Utilisation: 100, 100
        String gongliSexyUrl = "https://pic.rmb.bdstatic.com/bjh/down/4e168e78e07d8f742ede564b7ac9b8d1.jpeg";

        // 重点知识点！！！
        // 对于Glide.with(View|Context|Activity|Fragment|FragmentActivity) 传入的类型大有文章，他可以是View，可以是fragment, 可以是 Activity，甚至可以是applicationContext，不同的类型决定了图片请求链路的生命周期：
        // 如果传入的是Activity，则当当前请求请求图片的fragment容器销毁（已经被detach），fragment所在Activity容器没有销毁时，请求链路不会终止，当数据完成后还会继续回调onResourceReady，因为链路的生命周期跟随当前with传入的activity生命周期
        // 此时如果在onResourceReady里操作fragment就会导致一些异常，例如getChildFragmentManager时出现Fragment has not been attached yet.
        // 换言之，如果with传入的是当前fragment，如果fragment被销毁（按返回键），图片的请求链路也随之停止，不会再回调onResourceReady
        // 所以：在使用Glide时一定要重点关注Glide.with（）传入对象是哪种，如果生命周期过长会导致图片回来后回调onResourceReady，然后在onResourceReady里操作生命周期过短（已经被销毁对象fragment）时出现一些异常

        //Glide.with(getContext()) // 如果传getContext()，其实是host，即Activity，会出现fragment被销毁后，还会继续回调onResourceReady；
        Glide.with(this) // 如果传this, 即当前fragment，则当fragment销毁后不会回调onResourceReady
        //Glide.with(firstImg) // 如果传当前加载的view，则当fragment销毁后其实view自身也就消化了，也不会回调onResourceReady
            .load(gongliSexyUrl)
            .skipMemoryCache(true) // 为了测试禁用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) // 为了是测试禁用磁盘缓存
            .into(new CustomTarget<Drawable>() {
                @Override public void onResourceReady(@NonNull Drawable resource,
                    @Nullable Transition<? super Drawable> transition) {
                    // onResourceReady 在主线程回调
                    Log.d(TAG, "First ImageView:: onResourceReady()");
                    firstImg.setImageDrawable(resource);
                    // 通过charles 设置慢速网络，让图片经过好几秒才可以下载成功，在下载过程中按返回让当前fragment销毁，fragment已经被detach销毁后，还再继续getChildFragmentManager,最终导致FATAL EXCEPTION：main:
                    //java.lang.IllegalStateException: Fragment FirstFragment has not been attached yet.
                    FragmentManager childFragmentManager = getChildFragmentManager();
                    Log.d(TAG, "First ImageView:: onResourceReady() ====  getChildFragmentManager == " + childFragmentManager);
                }

                @Override public void onLoadCleared(@Nullable Drawable placeholder) {
                    Log.d(TAG, "First ImageView:: onLoadCleared()");


                }
            });
        //////////////////////////////////////////////////////////////////

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