package com.enjoy.ribs

/**
 * Created by xionglei01@baidu.com on 2022/9/26.
 */
interface IPresenterDelegate {

    fun moveMap(x: Int, y: Int)

    fun showMap()

    fun hideMap()

    fun updateLocationView(location: String)

}