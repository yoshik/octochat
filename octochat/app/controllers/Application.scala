package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    implicit request =>
    session.get("login").map { login =>
      Ok(views.html.index("Hello " + login))
    }.getOrElse {
      Ok(views.html.index("NotLogin"))
    }
  }
  
}