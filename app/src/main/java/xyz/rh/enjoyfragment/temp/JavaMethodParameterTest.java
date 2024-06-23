package xyz.rh.enjoyfragment.temp;

import java.util.ArrayList;

/**
 * Created by rayxiong on 2024/1/12.
 */
class JavaMethodParameterTest {

   // 在 Java 中讨论函数参数传递时，常见的疑问是参数是通过值传递还是引用传递。
   // Java 的参数传递可以总结为总是通过值传递。这意味着无论你传递的是基本类型还是引用类型的对象，传递给方法的始终是变量的副本，而不是原始变量本身。

   //基本类型
   //对于基本数据类型（如 int, float, char 等），当它们作为参数传递给方法时，传递的是变量的副本（即变量的值）。在方法内对这些参数的修改不会影响到原始数据。
   //
   //引用类型
   //对于引用类型（如数组、对象等），传递的也是值，但这个值是对堆中对象的引用的副本。这意味着在方法中你可以通过这个引用副本修改堆中的对象本身，但如果你尝试将引用本身指向另一个对象，这个修改不会影响到原始引用。换句话说，你可以修改对象的状态，但不能修改引用指向的对象。

   public static void main(String[] args) {
      JavaMethodParameterTest test = new JavaMethodParameterTest();
      Foo foo = new Foo();
      foo.name = "xiong";
      foo.age = 37;
      // 测试java方法的参数到底是值传递还是引用传递：结论：java方法里都是值传递
      test.testMethodParam(foo);

      // 用集合继续再次验证测试
      //ArrayList<Foo> list = new ArrayList<>();
      //list.add(foo); // 此时给list添加new出来的foo对象
      //test.testMethodParamList(list);

      // 最后看foo对象是否有属性变化
      System.out.println("after testMethodParam : foo.name = " + foo.name);
   }

   /**
    * 该方法通过这个副本引用修改了原对象的 name 属性，因此外部的原始 Foo 对象的状态发生了变化。
    * 然而，该当方法尝试将引用副本指向一个新的 Foo 对象，这个修改不会影响到原始引用，因为方法接收的是引用的副本。
    * @param param
    */
   void testMethodParam(Foo param) {
      //param.name = "这是在方法内部修改过的，改成了dodo";
      param = new Foo(); // 这个是论证java方法是值传递最有力的证据，在方法内部把入参指向一个全新的Foo，不会影响外部传进来的Foo对象
      param.name = "这是在方法内部重新new了一个Foo对象，然后把函数的参数param指向了它";
   }

   /**
    * @param list
    */
   void testMethodParamList(ArrayList<Foo> list) {
      // 在方法内部从入参list里拿出foo对象，然后修改foo对象的指向
      Foo foo = list.get(0);
      foo.name = "这是在方法内部重新new了一个Foo对象，然后把函数的参数param指向了它";
   }

}

class Foo {
   public String name;
   public int age;
}

