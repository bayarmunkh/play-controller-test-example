package controllers

import play.api.mvc._
import play.api.libs.ws._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait GoogleService {
  def getHomePage : Future[String] = {
    WS.url("http://google.com").get().map(_.body)
  }
}

trait ApplicationController { this: Controller =>

  val api: GoogleService

  def index = Action.async {
    api.getHomePage.map {
      Ok(_)
    }
  }
}

object Application extends ApplicationController with Controller {
  override val api = new GoogleService {}
}
