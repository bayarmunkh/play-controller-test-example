import controllers.{ApplicationController, GoogleService}
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.mvc.Controller
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  class TestApplicationController extends ApplicationController with Controller {
    // or use mokito here
    override val api = new GoogleService {
      override def getHomePage = Future {"Fake Google!"}
    }
  }

  val controller = new TestApplicationController

  "Application" should {

    "render the index page" in new WithApplication{
      val home = controller.index(FakeRequest(GET, "/index"))

      status(home) must equalTo(OK)
      contentAsString(home) must contain ("Fake Google!")
    }
  }
}
