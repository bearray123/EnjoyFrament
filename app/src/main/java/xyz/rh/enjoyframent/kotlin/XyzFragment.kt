package xyz.rh.enjoyframent.kotlin

/**
 * Created by rayxiong on 2022/11/1.
 */
class XyzFragment : IDataProvider {

    override fun <T> getData(): ArrayList<T> {
        return super.getData()
    }

}

fun main() {

//    KotlinMainActivity::javaClass.
//    var cls = KotlinMainActivity::class.java.genericSuperclass
//    println(cls)

    val kma = Something<EntityB>()

    println("kma::class.java==== ${kma::class.java}")
    println("kma.javaClass==== ${kma.javaClass}")
    println("kma::javaClass==== ${kma::javaClass}")

    println("EntityB::class.javaClass==== ${EntityB::class.java}")


    println(kma::class.java.genericSuperclass)




}