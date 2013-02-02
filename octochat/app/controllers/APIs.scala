package controllers

import lib._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.json._


object APIs extends Controller {
  val rootUrl = "https://api.github.com/"  
  val reposUrl ="user/repos"
  
  def repos(token:String):String = {
    WS.url(rootUrl+reposUrl+"?access_token="+token).get.value.get.body;
  }
  
  
}