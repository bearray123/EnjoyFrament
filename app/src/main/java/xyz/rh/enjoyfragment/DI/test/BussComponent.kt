package xyz.rh.enjoyfragment.di.test

import dagger.Component
import xyz.rh.enjoyfragment.MainActivity

/**
 * Created by xionglei01@baidu.com on 2022/10/2.
 */
@Component(modules = [ModuleB::class])
interface BussComponent {

    fun injectMainActivity(act: MainActivity)

}