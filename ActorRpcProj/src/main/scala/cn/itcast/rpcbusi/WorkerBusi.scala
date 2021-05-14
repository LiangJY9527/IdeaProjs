package cn.itcast.rpcbusi

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import cn.itcast.casedto.{InitResponseTask, InitTask, SendHeart, SendHeartBeat}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.language.postfixOps

class WorkerBusi(workName: String) extends Actor{
  val id = UUID.randomUUID().toString
  var master: ActorSelection= _
  override def preStart(): Unit = {
    master = context.actorSelection("akka.tcp://MasterSystem@192.168.217.20:2466/user/MasterBusi")
    val workIp = context.system.settings.config.getString("akka.remote.netty.tcp.hostname")
    val workPort = context.system.settings.config.getInt("akka.remote.netty.tcp.port")
    val workParent = context.system.settings.name
    master ! InitTask(id,workIp,workPort,workParent,workName)
  }

  override def receive: Receive = {
    case InitResponseTask(msg,parentInfo) =>
      println(msg+":"+parentInfo)
      //启动定时器发送心跳，先给自己发消息，再给master发消息
      //增强的功能，导入隐式转换
      import context.dispatcher
      context.system.scheduler.schedule(0 millis,10000 millis,self,SendHeartBeat)//每隔十秒钟给自己发消息
    case SendHeartBeat =>
      println("SendHeartBeat to master")
      master ! SendHeart(id)
  }
}
object WorkerBusi{
  def main(args: Array[String]): Unit = {
    val configStr =
      s"""
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = "192.168.217.20"
        |akka.remote.netty.tcp.port = 2467
      """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkerSystem",config)
    actorSystem.actorOf(Props(new WorkerBusi("WorkerBusi")),"WorkerBusi")
    actorSystem.awaitTermination()
  }
}
