package cn.itcast.rpc

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Worker extends Actor{

  var worker: ActorSelection = _
  override def preStart(): Unit = {
    //指定tcp通信的地址，端口，路径（加user是规定）
    worker = context.actorSelection("akka.tcp://MasterSystem@192.168.217.20:2466/user/Master")
    worker ! "connect"
  }

  override def receive: Receive = {
    case "reply" => println("a reply from master")
  }
}
object Worker{
  def main(args: Array[String]): Unit = {
    //stripMargin不能丢
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "192.168.217.20"
         |akka.remote.netty.tcp.port = 2467
      """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem是老大，辅助创建和监听actor，他是单例的
    val actorSystem = ActorSystem("WorkerSystem",config)
    //创建actor，并指定访问路径
    actorSystem.actorOf(Props(new Worker),"Worker")
    actorSystem.awaitTermination()
  }
}