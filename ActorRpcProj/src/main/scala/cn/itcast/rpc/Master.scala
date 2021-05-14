package cn.itcast.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Master extends Actor{

  println("constructor invoked")
  override def preStart(): Unit = {
    println("preStart invoked")
  }

  //用于接收消息
  override def receive: Receive = {
    case "connect" =>
      println("a client connected....")
      //给发消息者回复消息
      sender ! "reply"
    case "hello" =>
      println("hello ....")
  }
}

object Master{
  def main(args: Array[String]): Unit = {
//    val host = args(0)
//    val port = args(1).toInt
//    stripMargin不能丢
    val configStr =
      s"""
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = "192.168.217.20"
        |akka.remote.netty.tcp.port = 2466
      """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem是老大，辅助创建和监听actor，他是单例的
    val actorSystem = ActorSystem("MasterSystem",config)
    //创建actor，并指定访问路径
    val master = actorSystem.actorOf(Props(new Master),"Master")
//    master ! "hello"
    actorSystem.awaitTermination()
  }
}