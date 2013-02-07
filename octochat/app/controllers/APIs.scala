package controllers

import lib._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.json._
import controllers.Auth._


object APIs extends Controller {
  val rootUrl = "https://api.github.com/"  
  val subscriptionsUrl ="user/subscriptions"
  val usersUrl ="users/"
  
  def repos_row(token:String):String = {
    WS.url(rootUrl+subscriptionsUrl+"?access_token="+token).get.value.get.body
  }
  
  def rooms(token:String) ={
    val jsonString = repos_row(token)
    val json = Json.parse(jsonString)
    (json \\ "full_name").map(_.as[String]).sorted
  }
  
  def users_row(token:String,name:String):String = {
    WS.url(rootUrl+usersUrl+name+"?access_token="+token).get.value.get.body
  }
  
  def icon(name:String) = Action {
    implicit request =>
    val user = GithubUser.fromSession(session)
    val jsonString = users_row(user.token,name)
    val json = Json.parse(jsonString)
    Ok( views.html.echo( (json \ "avatar_url").as[String] ) )
  }

}