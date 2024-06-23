package xyz.rh.enjoyfragment.temp;

import java.util.ArrayList;

/**
 * Created by rayxiong on 2024/1/12.
 */
class JavaMethodParameterTest {

   // 在 Java 中讨论函数参数传递时，常见的疑问是参数是通过值传递还是引用传递。
   // Java 的参数传递可以总结为总是通过值传递。这意味着无论你传递的是基本类型还是引用类型的对象，传递给方法的始终是变量的副本，而不是原始变量本身。

   public static void main(String[] args) {
      JavaMethodParameterTest test = new JavaMethodParameterTest();
      Foo foo = new Foo();
      foo.name = "xiong";
      foo.age = 37;
      // 测试java方法的参数到底是值传递还是引用传递：结论：java方法里都是值传递
      //test.testMethodParam(foo);

      // 用集合继续再次验证测试
      ArrayList<Foo> list = new ArrayList<>();
      list.add(foo); // 此时给list添加new出来的foo对象
      test.testMethodParamList(list);

      // 最后看foo对象是否有属性变化
      System.out.println("after testMethodParam : foo.name = " + foo.name);
   }


   void testMethodParam(Foo param) {
      //param.name = "这是在方法内部修改过的，改成了dodo";
      param = new Foo(); // 这个是论证java方法是值传递最有力的证据，在方法内部把入参指向一个全新的Foo，不会影响外部传进来的Foo对象
      param.name = "这是在方法内部重新new了一个Foo对象，然后把函数的参数param指向了它";
   }

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

