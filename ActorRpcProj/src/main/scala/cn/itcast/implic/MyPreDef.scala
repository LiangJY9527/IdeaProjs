package cn.itcast.implic

import java.io.File

/**
  * Created by ZX on 2016/4/14.
  */
object MyPreDef {

  implicit def fileToRichFile(f: File) = {
    new RichFile(f)
  }

  implicit def girlToOrdered(girl:Girl)= new Ordered[Girl]{
    override def compare(that: Girl): Int = {
      if(girl.faceValue == that.faceValue) {
        girl.size - that.size
      } else {
        girl.faceValue - that.faceValue
      }
    }
  }

  implicit object girlOrdering extends Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if(x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }

  trait boyOrdering extends Ordering[Boy]{
    override def compare(x: Boy, y: Boy): Int = {
      if(x.faceValue == y.faceValue) {
        0
      } else {
        x.faceValue - y.faceValue
      }
    }
  }
  implicit object Boy extends boyOrdering
}
