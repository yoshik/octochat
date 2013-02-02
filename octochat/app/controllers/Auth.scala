package controllers

import lib._
import play.api.mvc._
import play.api.libs.json._


object Auth extends Controller {

  val GITHUB = new OAuth2[GithubUser](OAuth2Settings(
    "xxxx",//clientID
    "xxxxxxxxx",//clientSecret
    "https://github.com/login/oauth/authorize",
    "https://github.com/login/oauth/access_token",
    "https://api.github.com/user"
  )){
    def user(body: String) = Json.fromJson(Json.parse(body))
  }
  
  def signin()   = Action { Redirect(GITHUB.signIn) }
  def signout()  = Action { Redirect(routes.Application.index).withNewSession }
  def callback() = Action { implicit request =>
    params("code").flatMap { code =>
      GITHUB.authenticate(code) map { user =>
        Redirect(routes.Application.index).withSession(user.toSession(session,GITHUB.token))
      }
    } getOrElse Redirect(GITHUB.signIn)
  }
  
  protected def params[T](key: String)(implicit request: Request[T]) = request.queryString.get(key).flatMap(_.headOption)

  case class GithubUser(
    login: String,
    avatar_url: String,
    token: String
  ){
    def toSession(session: Session,token:String) = GithubUser.toSession(this, session,token)
  }

  implicit def GithubUserReads: Reads[GithubUser] = new Reads[GithubUser]{
    def reads(json: JsValue) =
      GithubUser(
        (json \ "login").as[String],
        (json \ "avatar_url").as[String],
        ""
      )
  }
  
  object GithubUser {
    def fromSession(session: Session): GithubUser = {
      GithubUser(
        session.get("login") match {
          case Some(v) => v
          case _ => ""
          },
        session.get("avatar_url") match {
          case Some(v) => v
          case _ => ""
          },
        session.get("token") match {
          case Some(v) => v
          case _ => ""
          }
      )
    }
    def toSession(formvalue: GithubUser, session: Session,token:String): Session = {
      {
        Map(
          "login" -> formvalue.login,
          "avatar_url" -> formvalue.avatar_url,
          "token" -> token
          ).foldLeft(session)(_ + _)
      }
    }
  }


}
