package controllers

import play.api._
import play.api.mvc._
import controllers.Auth._

object Room extends Controller {
  
  def view(owner:String,repo:String) = Action {
  implicit request =>
  session.get("login").map {login=>
      val user = GithubUser.fromSession(session)
      Ok(views.html.room(user.login)(user.avatar_url))
    }.getOrElse {
      Ok(views.html.index("NotLogin"))
    }
  }
}