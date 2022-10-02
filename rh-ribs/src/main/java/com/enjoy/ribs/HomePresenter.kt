package com.enjoy.ribs

/**
 * Created by xionglei01@baidu.com on 2022/9/26.
 */
class HomePresenter<T: IPresenterDelegate> (var pDelegate: IPresenterDelegate){

    lateinit var presenterDelegate: T




}