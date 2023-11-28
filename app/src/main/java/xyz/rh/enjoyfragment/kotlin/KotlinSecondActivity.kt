package xyz.rh.enjoyfragment.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by rayxiong on 2023/9/8.
 */
class KotlinSecondActivity: AppCompatActivity() {

    companion object {
        var callback: OnResultCallback? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        setResult(RESULT_OK, Intent().apply {
//            this.putExtra("result1", "第一个extra")
//            this.putExtra("result2", "第二个extra")
//        })

        callback?.onResult("哈哈哈哈")

    }


    override fun onDestroy() {
        super.onDestroy()
    }





}

interface OnResultCallback {
    fun onResult(ret: String)
}