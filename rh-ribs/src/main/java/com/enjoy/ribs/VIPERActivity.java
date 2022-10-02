package com.enjoy.ribs;

import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.enjoy.rib.VIPERFragment;

/**
 * Created by xionglei01@baidu.com on 2022/9/26.
 */
public class VIPERActivity extends AppCompatActivity {

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //ViewGroup decorView = findViewById(androidx.appcompat.R.id.content);
      //decorView.addView(c);

      setContentView(R.layout.viper_activity_layout);

      Fragment fragment = VIPERFragment.newInstance("","");
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.viper_fragment_container, fragment);
      fragmentTransaction.commit();

   }




}
