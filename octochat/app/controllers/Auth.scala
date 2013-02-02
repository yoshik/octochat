package controllers

import lib._
import play.api.mvc._
import play.api.libs.json._


object Auth extends Controller {

  val GITHUB = new OAuth2[GithubUser](OAuth2Settings(
    "ce99b5e59f36010082b4",//clientID
    "90bb8d04f89ba13db43beb784c69625288876851",//clientSecret
    "https://github.com/login/oauth/authorize",
    "https://github.com/login/oauth/access_token",
    "https://api.github.com/user"
  )){
    def user(body: String) = Json.fromJson(Json.parse(body))
  }

  case class GithubUser(
    login: String,
    avatar_url: String
  )

  implicit def GithubUserReads: Reads[GithubUser] = new Reads[GithubUser]{
    def reads(json: JsValue) =
      GithubUser(
        (json \ "login").as[String],
        (json \ "avatar_url").as[String]
      )
  }

  def signin() = Action { Redirect(GITHUB.signIn) }
  def signout() = Action { Redirect(routes.Application.index).withNewSession }

  def callback() = Action { implicit request =>
    params("code").flatMap { code =>
      GITHUB.authenticate(code) map { user =>
        Redirect(routes.Application.index).withSession(("login" -> user.login),("avatar_url" -> user.avatar_url))
      }
    } getOrElse Redirect(GITHUB.signIn)
  }

  protected def params[T](key: String)(implicit request: Request[T]) = request.queryString.get(key).flatMap(_.headOption)
}
