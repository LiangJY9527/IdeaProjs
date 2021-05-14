package cn.itcast.casedto

import scala.util.parsing.json.JSONObject


class WorkDto {

}
case class QuestTask(inDto: InDto)
case class ResponseTask(outDto: String)
//worker -> master
case class InitTask(id: String, ip: String, port: Int,name: String,childName: String) extends Serializable
//master -> worker
case class InitResponseTask(msg: String,parentInfo: String)extends Serializable
//worker -> master
case class SendHeart(workId: String)extends Serializable
//master -> master
case object ChectTimeOutWorker
//worker -> worker
case object SendHeartBeat

case class InDto(header: String,oprInfo: String,busiInfo: JSONObject)