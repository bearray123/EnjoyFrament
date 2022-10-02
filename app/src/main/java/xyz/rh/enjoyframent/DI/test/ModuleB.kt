package xyz.rh.enjoyframent.di.test

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import dagger.Module
import dagger.Provides

/**
 *
 * Module存在的意义：
 * 项目中使用到了
 * 第三方的类库，第三方类库又不能修改，所以根本不可能把Inject注解加入这些类中，
 * 这时我们的Inject就失效了。那我们可以封装第三方的类库，封装的代码怎么管理呢，
 * 总不能让这些封装的代码散落在项目中的任何地方，总得有个好的管理机制，那Module就可以担当此任。
 *
 * Created by xionglei01@baidu.com on 2022/10/2.
 */
@Module
class ModuleB {
    // 这里的BussB是模拟第三方库里面的类，因为第三方库不能直接给对应的类加@inject注解
    // 所以加了一层中间层Module，通过Module中的@Provides注解来直接new出来三方库里的对象。

    /**
     * @Module 和 @Provides 注解是为解决第三方类库而生的，Module是一个简单工厂模式，
     * Module可以包含创建类实例的方法，这些方法用Provides来注解
     */

    @Provides
    fun providerBussB(): BussBFrom3rdParty {
        return BussBFrom3rdParty()
    }


}