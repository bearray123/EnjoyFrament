package xyz.rh.enjoyfragment.kotlin.generic

/**
 * 泛型的协变，逆变
 *  PECS原则：Producer extends, Consumer super
 * Created by rayxiong on 2022/11/23.
 */
class Action<out T: Animal> {

    fun invokeShouting(performer: @UnsafeVariance T) { // 作为方法参数，这里是消费T, 消费用in == super
        performer?.shouting()
//        performer = null
    }

//    fun getPerformer() : @UnsafeVariance T { // 作为返回值，这里是生产T，生产用out == extends
//        return Dog() as T
//    }

}

interface ABC<T> {
    fun play(p1: String , p2: T)
}


fun main() {
    println("测试 泛型")
    val dog = Dog()
    val cat = Cat()
    val animal = Animal()

//    var dogList = arrayListOf<Dog>(dog)
//    var animalList = arrayListOf<Animal>(animal)
//    animalList = dogList  // 报错
//    Comparable
//    List

    // 协变(out)：泛型类（Action<out: T>）的类型与泛型实参继承关系相同，例如，Dog是Animal的子类，那么Action<Dog> 是 Action<Animal> 的子类；协变用于生产者，JDK源码例子：List<out E> ，这里的List是kotlin中的List，与java是不一样的，kotlin中List是只读的，因为要达到只读效果所以才使用了协变来实现，点进去看Kotlin的List源码发现没有add,remove这种修改方法
    // 逆变(in)： 泛型类（Action<in: T>）的类型与泛型实参继承关系相反，例如，Dog是Animal的子类，那么Action<Dog> 是 Action<Animal> 的父类；逆变用于消费者， JDK源码例子：Comparable<in T>
    var dogAction: Action<*> = Action<Dog>()
    var labradorDogAction = Action<LabradorDog>()
    var corgiDogAction = Action<CorgiDog>()
    var animalAction = Action<Animal>()
//    dogShoutingAction.invokeShouting(dog)

//    animalAction = dogAction

//    Array
//    ArrayList

//    var mulist = MutableList<String>()

    val intList = arrayListOf(1,2,3)
    val strList = arrayListOf("a","b","c")

//    printList(intList)

//    val oo = object : ABC<Dog> {
//        override fun play(p1: String, p2: Dog) {
//            println("12345678")
//        }
//
//    }
//    val list1 = arrayListOf<ABC<Dog>>()
//    list1[1].play("", Dog())
//    list1[2] = oo


    ////////////逆变
    val labradorDog = LabradorDog()
    labradorDog.size = 100
    val corgiDog = CorgiDog()
    corgiDog.size = 90

//    labradorDog > corgiDog  // 因为compareable接口重载了运算符，所以可以直接用>来比较
    if (labradorDog.compareTo(corgiDog) > 0) {
        println(" 拉布拉多 比 柯基 大")
    } else {
        println(" 拉布拉多 比 柯基 小")
    }


//    val intComparable = object : Comparable<Int> {
//        val i = 100
//        override fun compareTo(other: Int): Int {
//            return (this.i - other)
//        }
//
//    }
//    val floatComparable = object : Comparable<Float> {
//        val f = 130.1f
//        override fun compareTo(other: Float): Int {
//            return (this.f - other)
//        }
//    }

//    val data1: Action<*>? = Action<LabradorDog>()
//    val re = data1?.invokeShouting()


}

//fun sort(comparable: Comparable<Number>) {
//    comparable.compareTo()
//}

fun printList(list: MutableList<Any>) {

    list.add(0.01f)

}