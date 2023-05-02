package xyz.rh.enjoyframent.kotlin.generic;

/**
 * Created by rayxiong on 2022/11/23.
 */
class ActionJ<T extends Runnable> {

   public T dowork(T dota) {
      dota.run();
      dota = null;
      return dota;
   }

}
