package cn.itcast.rpcbusi

class WorkInfo(val id:String,val ip: String,val port: Int,val name: String,val childName: String) {
  var lastHeartBeatTime: Long = _
}
