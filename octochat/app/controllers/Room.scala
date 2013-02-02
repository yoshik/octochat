package controllers

import play.api._
import play.api.mvc._
import controllers.Auth._
import controllers.Chat._
import models.ChatModel

object Room extends Controller {
  
  def view(owner:String,repo:String) = Action {
  implicit request =>
  session.get("login").map {login=>
      val user = GithubUser.fromSession(session)
      Ok(views.html.room(user.login, user.avatar_url, owner, repo)(ChatModel.roomMessage(owner,repo), messageForm))
    }.getOrElse {
      Ok(views.html.index())
    }
  }
  
  def timeline = Action { implicit request =>
    val user = GithubUser.fromSession(session)
    val rooms = APIs.rooms(user.token)
    Ok(views.html.timeline(ChatModel.all().filter( post =>
      rooms.contains(post.owner + "/" + post.repo)
    )))
  }
  
  
}