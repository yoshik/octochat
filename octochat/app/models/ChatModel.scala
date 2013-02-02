package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class ChatModel(id: Long, message: String, owner:String, repo:String, author:String)

object ChatModel {

  val chatmodel = {
    get[Long]("id") ~ 
    get[String]("message") ~
    get[String]("owner") ~
    get[String]("repo") ~
    get[String]("author") map {
      case id ~ message ~ owner ~ repo ~ author => ChatModel(id, message, owner, repo, author)
    }
  }

  def all(): List[ChatModel] = DB.withConnection { implicit c =>
    SQL("select * from chatmodel").as(chatmodel *)
  }
  
  def roomMessage(owner:String,repo:String): List[ChatModel] = DB.withConnection { implicit c =>
    SQL("select * from chatmodel").as(chatmodel *)
  }

  def create(message: String, owner:String, repo:String, author:String) {
      DB.withConnection { implicit c =>
      SQL("insert into chatmodel (message,owner,repo,author) values ({message},{owner},{repo},{author})").on(
        'message -> message,
        'owner -> owner,
        'repo -> repo,
        'author -> author
      ).executeUpdate()
    }
  }
}