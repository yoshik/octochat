package models

case class ChatModel(id: Long, message: String, author:String, icon:String, owner:String, repo:String)

object ChatModel {
  def all(): List[ChatModel] = Nil
  def create(message: String, owner:String, repo:String, author:String) {}  
}