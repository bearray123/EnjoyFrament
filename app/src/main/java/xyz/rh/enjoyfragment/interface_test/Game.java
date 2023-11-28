package xyz.rh.enjoyfragment.interface_test;

/**
 * Created by xionglei01@baidu.com on 2022/10/15.
 */
class Game<A extends ABC, H extends HGK, X extends XYZ> {

   public A build() {
      return ((A) new Object());
   }

   public <T> T test() {
      Object o = new Object();
      //if (o instanceof A) {
      //
      //}
      return ((T) new Object());
   }


}
