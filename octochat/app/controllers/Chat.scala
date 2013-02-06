package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.WebSocket
import play.api.data._
import play.api.data.Forms._
import controllers.Auth._
import models._

object Chat extends Controller {
    
  def post(owner:String,repo:String) = Action { implicit request =>
    messageForm.bindFromRequest.fold(
      errors => Redirect(routes.Room.view(owner,repo)),
      message => {
        val user = GithubUser.fromSession(session)
        ChatModel.create(message,owner,repo,user.login)
        Redirect(routes.Room.view(owner,repo))
      }
    )
  }
  
  
  val messageForm = Form(
    "message" -> nonEmptyText
  )

}