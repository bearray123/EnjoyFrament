package xyz.rh.enjoyfragment.kotlin

/**
 * Created by rayxiong on 2022/11/1.
 */
interface IDataProvider {


    fun <T> getData() : ArrayList<T> {
        val data = arrayListOf<T>()
        return data
    }




}