package xyz.rh.enjoyframent.layoutparams

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import xyz.rh.enjoyframent.R

/**
 * Created by rayxiong on 2023/3/11.
 */
class TestLayoutParamsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout_params_page_layout)

        val newUserLayout = findViewById<NewUserLayout>(R.id.new_user_layout_container)
        val viewStub = findViewById<ViewStub>(R.id.xiaorentou_container_view_stub)
        viewStub.visibility = View.VISIBLE

    }


}