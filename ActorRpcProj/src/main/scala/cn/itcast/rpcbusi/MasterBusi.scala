package cn.itcast.rpcbusi

import akka.actor.{Actor, ActorSystem, Props}
import cn.itcast.casedto._
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

class MasterBusi(masterName: String) extends Actor{
  val idToWorks = new mutable.HashMap[String,WorkInfo]()
  val workInfos = new mutable.HashSet[WorkInfo]()
  val CHECK_INTERVAL = 15000

  //worker超时检测
  override def preStart(): Unit = {
    //增强的功能，导入隐式转换
    import context.dispatcher
    context.system.scheduler.schedule(0 millis,CHECK_INTERVAL millis,self,ChectTimeOutWorker)
  }

  override def receive: Receive = {
    case InitTask(id,ip,port,name,childName) =>
      println(s"$id = $ip:$port/$name/$childName")
      val workInfo = new WorkInfo(id,ip,port,name,childName)
      idToWorks(id) = workInfo
      workInfos += workInfo
      sender ! InitResponseTask("注册成功。。。","akka.tcp://MasterSystem@192.168.217.20:2466/user/MasterBusi")
    case SendHeart(id) =>
      if (idToWorks.contains(id)){
        val workInfo = idToWorks(id)
        //报活
        val currentTime = System.currentTimeMillis()
        workInfo.lastHeartBeatTime = currentTime
      }
    case ChectTimeOutWorker =>{
      val removeWorks = workInfos.filter(System.currentTimeMillis()-_.lastHeartBeatTime > CHECK_INTERVAL)
      for (w <- removeWorks){
        workInfos -= w
        idToWorks -= w.id
      }
      println(workInfos.size)
    }

  }
}
object MasterBusi{
  def main(args: Array[String]): Unit = {
    val configStr =
      s"""
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = "192.168.217.20"
        |akka.remote.netty.tcp.port = 2466
      """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("MasterSystem",config)
    val master = actorSystem.actorOf(Props(new MasterBusi("MasterBusi")),"MasterBusi")
    actorSystem.awaitTermination()
  }
}