package xyz.rh.enjoyframent.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.rh.enjoyframent.R

/**
 *
 * DeepLink启动方式：adb shell am start -a android.intent.action.VIEW -d "rayhung://dodohost"
 * 注意：scheme://host  scheme是区分大小写的！！！host不用区分大小写！！！可以在manifest里配置path
 *
 * Created by rayxiong on 2023/5/5.
 */
class DeepLinkActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.deeplink_activity_layout)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

// 以下摘录官方文档：https://developer.android.com/training/app-links/deep-linking?hl=zh-cn

//如需创建指向您应用内容的链接，请在清单中添加一个包含以下元素和属性值的 intent 过滤器：
//<action>
//    指定 ACTION_VIEW intent 操作，以便能够从 Google 搜索中访问此 intent 过滤器。
//<data>
//    添加一个或多个 <data> 标记，每个标记都代表一个解析为 Activity 的 URI 格式。<data> 标记必须至少包含 android:scheme 属性。
//您可以添加更多属性，以进一步细化 Activity 接受的 URI 类型。例如，您可能拥有多个接受相似 URI 的 Activity，这些 URI 只是路径名称有所不同。在这种情况下，请使用 android:path 属性或其 pathPattern 或 pathPrefix 变体区分系统应针对不同 URI 路径打开哪个 Activity。
//
//<category>
//    包含 BROWSABLE 类别。如果要从网络浏览器中访问 Intent 过滤器，就必须提供该类别。否则，在浏览器中点击链接便无法解析为您的应用。
//此外，还要包含 DEFAULT 类别。这样您的应用才可以响应隐式 intent。否则，只有在 intent 指定您的应用组件名称时，Activity 才能启动。
//
//以下 XML 代码段展示了如何在清单中为深层链接指定 intent 过滤器。URI “example://gizmos” 和 “http://www.example.com/gizmos” 都会解析到此 Activity。
//<activity
//    android:name="com.example.android.GizmosActivity"
//    android:label="@string/title_gizmos" >
//    <intent-filter android:label="@string/filter_view_http_gizmos">
//        <action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.BROWSABLE" />
//        <data android:scheme="http"
//                android:host="www.example.com"
//                android:pathPrefix="/gizmos" />
//    </intent-filter>
//    <intent-filter android:label="@string/filter_view_example_gizmos">
//        <action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.BROWSABLE" />
//        <data android:scheme="example"
//        android:host="gizmos" />
//    </intent-filter>
//</activity>

