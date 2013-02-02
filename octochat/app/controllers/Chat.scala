package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.WebSocket

import models._
import play.api.data._
import play.api.data.Forms._

object Chat extends Controller {
    
  def post(owner:String,repo:String,author:String) = Action { implicit request =>
    messageForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index()),
      message => {
        ChatModel.create(message,owner,repo,author)
        Redirect(routes.Room.view(owner,repo))
      }
    )
  }
  
  
  val messageForm = Form(
    "message" -> nonEmptyText
  )

}