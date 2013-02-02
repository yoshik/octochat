package controllers

import play.api._
import play.api.mvc._
import controllers.Auth._
import controllers.APIs._

object Application extends Controller {
  
  def index = Action {
    implicit request =>
    session.get("login").map {login=>
        val user = GithubUser.fromSession(session)
        Ok(views.html.login(user.login)(user.avatar_url)(APIs.rooms(user.token)))
    }.getOrElse {
      Ok(views.html.index("NotLogin"))
    }
  }
  
}