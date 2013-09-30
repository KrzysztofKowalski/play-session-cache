import com.github.krzysztofkowalski.sessionCache._
import play.api.mvc._
import play.api.test.{WithApplication, FakeRequest, Helpers}
import scala.concurrent.{Future, ExecutionContext}


import org.specs2.mutable._
import play.api.test._
import models._
import scala.util.Success
import scala.util.Success

object SessionCacheSpec extends Specification with Results {

  implicit val timeout = akka.util.Timeout(512)

  "SessionCache" should {

    "without cookie" in new WithApplication() {
      var sessionPrefix: String = null
      val action = SessionCache {
        sessionCache => Action {
          sessionPrefix = sessionCache.asInstanceOf[CacheWrapper].prefix
          Ok
        }
      }
      val parsedRequest = action.parser(FakeRequest()).run.value.get
      parsedRequest.isSuccess mustEqual true
      var cookieValue: String = null
      val cookieSet = (parsedRequest.get: @unchecked) match {
        case Right(xbody) => {
          val f = FakeRequest().withBody(xbody)
          val result = action(f)
          val cookies = Helpers cookies result
          (cookies get SessionCache.cookieName) must beLike {
            case Some(s) => {
              cookieValue = s.value
              s.value.size must be_>(0)
            }
          }
        }
      }
      cookieSet and cookieValue === sessionPrefix
    }

    "with cookie" in new WithApplication() {
      var sessionPrefix: String = null
      val action = SessionCache {
        sessionCache =>
          Action {
            sessionPrefix = sessionCache.asInstanceOf[CacheWrapper].prefix
            Ok
          }
      }
      val parsedRequest = action.parser(FakeRequest() withCookies SessionCache.cookie("abc")).run.value.get
      parsedRequest.isSuccess mustEqual true
      val cookieNotSet = (parsedRequest.get: @unchecked) match {
        case Right(xbody) => {
          val f = FakeRequest().withBody(xbody)
          val result = action(f)
          val cookies = Helpers cookies result
          (cookies get SessionCache.cookieName) must beLike {
            case None => ok
          }
        }
      }
      cookieNotSet and sessionPrefix === "abc"
    }
  }
}