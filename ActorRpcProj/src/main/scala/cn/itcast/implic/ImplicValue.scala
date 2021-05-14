package cn.itcast.implic

//所有的定义的隐式转换必须放到object中
object Context{
  implicit val a = "caij"
}

object ImplicValue {
  //简单的隐式转换
  def sayHi()(implicit name: String = "laj") = {
    println(s"hi~~~ $name")
  }

  def main(args: Array[String]): Unit = {
    import Context._
    sayHi()("adas")
  }
}
