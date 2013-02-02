package controllers

import lib._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.json._


object APIs extends Controller {
  val rootUrl = "https://api.github.com/"  
  val subscriptionsUrl ="user/subscriptions"
  
  def repos_row(token:String):String = {
    WS.url(rootUrl+subscriptionsUrl+"?access_token="+token).get.value.get.body;
  }
  
  def rooms(token:String) ={
    val jsonString = repos_row(token:String);
    val json = Json.parse(jsonString)
    
    (json \\ "full_name").map(_.as[String]).sorted

  }
}