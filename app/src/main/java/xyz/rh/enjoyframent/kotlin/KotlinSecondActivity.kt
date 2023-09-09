package xyz.rh.enjoyframent.kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by rayxiong on 2023/9/8.
 */
class KotlinSecondActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_OK, Intent().apply {
            this.putExtra("result1", "第一个extra")
            this.putExtra("result2", "第二个extra")
        })
    }


    override fun onDestroy() {
        super.onDestroy()
    }



}