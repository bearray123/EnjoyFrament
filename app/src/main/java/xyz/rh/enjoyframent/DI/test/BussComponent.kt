package xyz.rh.enjoyframent.di.test

import dagger.Component
import xyz.rh.enjoyframent.MainActivity

/**
 * Created by xionglei01@baidu.com on 2022/10/2.
 */
@Component(modules = [ModuleB::class])
interface BussComponent {

    fun injectMainActivity(act: MainActivity)

}