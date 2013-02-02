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
  
  def timeline = Action {
    implicit request =>
    session.get("login").map {login=>
      val user = GithubUser.fromSession(session)
      Ok(views.html.timeline(user.login, user.avatar_url)(ChatModel.all()))
    }.getOrElse {
      Ok(views.html.index())
    }
  }
  
  
}