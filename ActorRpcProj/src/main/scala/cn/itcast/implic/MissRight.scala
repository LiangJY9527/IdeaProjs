package cn.itcast.implic

/**
  * Created by ZX on 2016/4/14.
  */
class MissRight[T] {
  //相当于viewbound
  def choose(first: T, second: T)(implicit ord : T => Ordered[T]): T = {
    if(first > second) first else second
  }
  //相当于
  def select(first: T, second: T)(implicit ord : Ordering[T]): T ={
    if(ord.gt(first, second)) first else second
  }

  def random(first: T, second: T)(implicit ord : Ordering[T]): T ={
    import Ordered.orderingToOrdered
    if(first > second) first else second
  }

}

object MissRight {
  def main(args: Array[String]) {
    val mr = new MissRight[Girl]
    val g1 = new Girl("hatanao", 98, 28)
    val g2 = new Girl("sora", 95, 33)

    import MyPreDef._
//    val g = mr.choose(g1, g2)
    val g = mr.select(g1, g2)
    println(g.name)

    val mr1 = new MissRight[Boy]
    val b1 = new Boy("hatanao", 98)
    val b2 = new Boy("sora", 95)
    val b3 = mr1.select(b1,b2)
    println(b3.name)
  }
}
