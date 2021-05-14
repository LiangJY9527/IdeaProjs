package cn.itcast.implic

import java.io.File

import scala.io.Source
//隐式转换，可以单独定义一个类
/*object MyPredef {
  implicit def fileToRichFile(f: File) = {
    new RichFile(f)
  }
}*/

class RichFile(f: File) {
  def read() = Source.fromFile(f).mkString
}

object RichFile {
  def main(args: Array[String]): Unit = {
    val f = new File("D:\\ztest\\hello.txt")
    //装饰，显示转换
    //    val txt = new RichFile(f).read()
    import MyPreDef._
    val txt = f.read()
    println(txt)
  }
}
