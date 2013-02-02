package lib

import play.api.libs.ws.WS
import play.core.parsers._

case class OAuth2Settings(
  clientId: String,
  clientSecret: String,
  signInUrl: String,
  accessTokenUrl: String,
  userInfoUrl: String
)

abstract class OAuth2[T](settings: OAuth2Settings){
  def user(body: String): T

  import settings._
  lazy val signIn = signInUrl + "?client_id=" + clientId

  def requestAccessToken(code: String): Option[String] = {
    val resp = WS.url(accessTokenUrl +
      "?client_id=" + clientId +
      "&client_secret=" + clientSecret +
      "&code=" + code).get.value.get.body
    FormUrlEncodedParser.parse(resp).get("access_token").flatMap(_.headOption)
  }

  def authenticate(code: String) = requestAccessToken(code).map(requestUserInfo)

  def requestUserInfo(accessToken: String): T =
    user(WS.url(userInfoUrl + "?access_token=" + accessToken).get.value.get.body)
}
