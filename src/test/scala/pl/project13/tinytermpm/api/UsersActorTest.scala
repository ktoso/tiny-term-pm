package pl.project13.tinytermpm.api

import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import akka.testkit.TestKit
import akka.actor.TypedActor
import pl.project13.tinytermpm.util.{ApiPreferences, Preferences}


class UsersActorTest extends FlatSpec with ShouldMatchers {

  val prefs = new ApiPreferences {
    val ServerUrl = "http://demo.tinypm.com/api"
    val ApiKey = "demo"
  }

  val actor = TypedActor.newInstance(classOf[UsersApi], new UsersActor(prefs), 10000)

  "UsersActor" should "fetch all available users" in {
    val users = actor.allUsers()

    users should not be ('empty)
    users.head.getName should equal ("Administrator")
  }
}