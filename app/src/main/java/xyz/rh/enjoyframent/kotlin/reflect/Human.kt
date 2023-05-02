package xyz.rh.enjoyframent.kotlin.reflect

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.reflect.ParameterizedType

/**
 * Created by rayxiong on 2022/11/20.
 */
class Human : SuperObject<Cloneable>(), InterfaceA<Runnable>, InterfaceB<Int, String>, InterfaceC {

    override fun playFromA(): Runnable {
        return Runnable {

        }
    }

    override fun playB(t: Int, k: String) {
    }

    override fun playC() {
    }

    override fun playX() {
    }


}


fun main() {
    println("test interface!!!")

    // Returns the Types representing the interfaces directly implemented by the class or interface represented by this object.
    // Class.genericInterfaces 返回的是该类的直接父类接口
    var types = Human::class.java.genericInterfaces

    types.forEach {
        // 会打印出：不会打印InterfaceX，因为没直接实现X接口
        // xyz.rh.enjoyframent.kotlin.reflect.InterfaceA<java.lang.Runnable>
        // xyz.rh.enjoyframent.kotlin.reflect.InterfaceB
        // xyz.rh.enjoyframent.kotlin.reflect.InterfaceC
        println("打印types: $it")
        if (it is ParameterizedType) {
            // 官方解释：ParameterizedType represents a parameterized type such as Collection<String>.
            // 这里会打印：xyz.rh.enjoyframent.kotlin.reflect.InterfaceA<java.lang.Runnable>
            // xyz.rh.enjoyframent.kotlin.reflect.InterfaceB<java.lang.Integer, java.lang.String>
            // 这个类型是带参数的类型，参数化类型
            println("it is ParameterizedType:: it=$it")
        }
        println()
    }

    println("-------------------")
    // Class.genericSuperclass取到的是带泛型参数的class类型 xyz.rh.enjoyframent.kotlin.reflect.SuperObject<java.lang.Cloneable>
    println("Human::class.java.genericSuperclass=== ${Human::class.java.genericSuperclass}")

    println("-------------------")
    // Class.superclass取到的是不带泛型参数的class类型：xyz.rh.enjoyframent.kotlin.reflect.SuperObject
    println("Human::class.java.superclass=== ${Human::class.java.superclass}")

    println("-------------------")
    ////// A.isAssignableFrom（B）用法介绍：A标识父类、父接口、本类 ， B代表本类
    println("Human::class.java.isAssignableFrom(InterfaceC::class.java) ==== ${Human::class.java.isAssignableFrom(InterfaceC::class.java)}") // false
    println("InterfaceC::class.java.isAssignableFrom(Human::class.java) ==== ${InterfaceC::class.java.isAssignableFrom(Human::class.java)}") // true

    println("SuperObject.isAssignableFrom(Human::class.java) ==== ${SuperObject::class.java.isAssignableFrom(Human::class.java)}") // true
    println("SuperSuperObject.isAssignableFrom(Human::class.java) ==== ${SuperSuperObject::class.java.isAssignableFrom(Human::class.java)}") // true


    println("-------------------")
    println("SuperObject  isAssignableFrom(Human::class.java) ==== ${SuperObject::class.java.isAssignableFrom(Human::class.java)}") // true
    println("SuperSuperObject  isAssignableFrom(Human::class.java) ==== ${SuperSuperObject::class.java.isAssignableFrom(Human::class.java)}") // true

    println("---------animal vs human----------")
    val animal = Animal()
    val human = Human()
    val animalSuperClass = animal.javaClass.superclass
    val humanSuperClass = human.javaClass.superclass
    val humanGenericSuperclass = human.javaClass.genericSuperclass
    val animalGenericSuperclass = animal.javaClass.genericSuperclass


    println("animal.javaClass.superclass ?= human.javaClass.superclass ::: ${animalSuperClass == humanSuperClass}") // true
    println("animal.javaClass.superclass == ${animal.javaClass.superclass}") // SuperObject
    println("human.javaClass.genericSuperclass == $humanGenericSuperclass") // SuperObject<java.lang.Cloneable>
    println("animal.javaClass.genericSuperclass == $animalGenericSuperclass") // SuperObject<java.lang.Cloneable>


    println("animal.javaClass.superclass ?= human.javaClass.humanGenericSuperclass ::: ${animalSuperClass == humanGenericSuperclass}") // false，因为human取父类时采用的是带泛型参数的父类，所以跟animal取的父类不是一种class类型

    println("humanGenericSuperclass ?= animalGenericSuperclass ::: ${humanGenericSuperclass == animalGenericSuperclass}") // false，因为human带泛型参数的父类是SuperObject<java.lang.Cloneable>，animal带泛型参数的父类是SuperObject<java.lang.Runnable>






    val abc = object : InterfaceA<String> {
        override fun playFromA(): String {
            TODO("Not yet implemented")
        }
    }




}