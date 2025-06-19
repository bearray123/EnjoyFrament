package xyz.rh.enjoyfragment.temp

/**
 * 分析kotlin的型变
 * Created by rayxiong on 2025/6/18.
 */

fun main() {

    println(123)

    // Kotlin 的函数类型本身参数是 逆变 (in)，返回值是 协变（out）
    // 如何理解这句话：
    // 比如 Animal是Dog的父类，当一个函数f1参数是Animal时，另一个函数f2参数是Dog时，返回值一样，那么这个函数类型是逆变的。
    // 什么意思？逆变就是逆向变化的类型，正常顺着变就是 Animal可以指向Dog，逆变就是 Dog可以指向Animal，也就是f2可以指向f1
    // 即：
    // Dog 是 Animal 的子类型
    // 但在函数参数中，类型关系“反过来”：(Dog) => void 是 (Animal) => void 的子类型 // 这是用js表达的箭头函数
    var f1: (Animal) -> Unit = { animal -> }
    var f2: (Dog) -> Unit = { dog -> }

//    f1 = f2
//    f2 = f1

    var f3 : (Boolean) -> Animal = { Animal()}
    var f4 : (Boolean) -> Dog = { Dog() }
    f3 = f4
//    f4 =f3

    var p1 = Producer<Animal>()
    var p2 = Producer<Dog>()
    // 让泛型类的泛型参数进行协变 out，这样就可以实现 p1 = p2
    p1 = p2

}

open class Animal {
    fun isAlive() {

    }
}

class Dog : Animal() {
    fun hasIQ() {

    }
}

// 泛型参数不加in out限制的话默认是不变的，java和kt的泛型默认都是不变，除非手动加关键字控制
// 比如加了 out 后可以让 Producer<out T: Animal> 这个类型协变，加了in后可以让其逆变。
// 协变，逆变后的效果可以达到传值，赋值的效果。但缺点就是限制了这个类内部的一些行为，比如out协变后，T 只能用在生产输出的地方（比如返回值）
// 比如in逆变后，T 只能用在输入，消费的地方（比如函数参数）
class Producer<out T: Animal> {
//class Producer<T: Animal> {
    fun produce() : T {
        return Any() as T
    }
}

class Consumer<T> {
    fun consume(data: T): T {
        return Any() as T
    }
}


