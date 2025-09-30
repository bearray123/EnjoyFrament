package xyz.rh.enjoyfragment.wan_glide

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R

class WanGlideActivity : BaseActivity() {

    private val carImageView: ImageView by lazy {
        findViewById(R.id.car_gif_img)
    }

    private val switchButton: Button by lazy {
        findViewById(R.id.switch_gif)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan_glide_layout)

        val gif1 = "https://s3-gz01.didistatic.com/packages-mait/img/eyl3uc5kzq1756438199885.gif"
        val gif2 = "https://s3-gz01.didistatic.com/packages-mait/img/GZSPqEnX9M1756438208577.gif"
        val gitController = GifController(carImageView, gif1, gif2)
        gitController.init()

        switchButton.setOnClickListener {
            gitController.onClick()
        }
    }
}