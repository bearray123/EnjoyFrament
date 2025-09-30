package xyz.rh.enjoyfragment.didihierarchy

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import xyz.rh.enjoyfragment.BaseActivity
import xyz.rh.enjoyfragment.R

class TestFragmentsHierarchyActivity : BaseActivity() {

    private val mainStackContainer: MyFrameLayout by lazy {
        findViewById(R.id.main_stack_layout)
    }

    private val mapContainer: MyFrameLayout by lazy {
        findViewById(R.id.map_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragments_hierarchy)

        findViewById<MyFrameLayout>(R.id.main).setLayoutType(LayoutType(1, "Activity的根布局"))

        mapContainer.setLayoutType(LayoutType(2, "地图Fragment的容器"))
        mainStackContainer.setLayoutType(LayoutType(3, "业务首页Fragment的容器"))


        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.map_layout, MapFragment())

        transaction.add(R.id.main_stack_layout, HomeFragment())

        transaction.commitAllowingStateLoss()



    }
}