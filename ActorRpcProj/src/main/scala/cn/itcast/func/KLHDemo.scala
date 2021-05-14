package cn.itcast.func

object KLHDemo {

  //定义普通方法一：
  def m1(x: Int): Int = x * x
  //定义普通方法二(柯里化方法)：
  def m2: Int => Int = x=> x * x
//  def m2(): Int => Int = x=> x * x
  //定义普通方法三(柯里化方法)：
  def m3= (x: Int)=> x * x
//  def m3()= (x: Int)=> x * x

  //定义函数一：
  val func1 = (x: Int) => x * x
  //定义函数二
  val func2: Int => Int = x => x * x

  //柯里化一
  def k1(x: Int)(y: Int):Int = {
    x * y
  }
  //柯里化二：相当于入参是一个元素，出参是一个方法
  def k2(x: Int): Int => Int=(y: Int) => {
    x * y
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5,6)
//    val a1 = arr.map(m1 _)
    val a1 = arr.map(m2)
//    val a1 = arr.map(m3)
//    val a1 = arr.map(func1)
    //函数都进行了隐式转换
//    val a1 = arr.map(func2)
    println(a1.toBuffer)
    println(k1(3)(4))
    println(k2(3)(5))
  }
}
