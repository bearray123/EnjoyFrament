package xyz.rh.enjoyfragment.kotlin.generic

/**
 * 狗狗父类
 * Created by rayxiong on 2022/11/23.
 */
// 这里没理解为什么这里Comparable使用逆变？？？
//open class Dog: Animal(), Comparable<Dog>{ // 这里Comparable<in T> 使用的是逆变
open class Dog: Animal(), MyComparable<Dog>{

//      var s: Float
//      var ss: Number

        var size: Int = 0

    override fun shouting() {
        println("一只狗狗 正在叫。。。")
    }

    override fun compareTo(other: Dog): Int {
        return this.size - other.size
    }

}