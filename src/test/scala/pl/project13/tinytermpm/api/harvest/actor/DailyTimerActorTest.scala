package pl.project13.tinytermpm.api.harvest.actor

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import akka.actor.TypedActor
import akka.util.duration._
import pl.project13.tinytermpm.api.tinypm.UserStoriesApi
import pl.project13.tinytermpm.api.tinypm.actor.UserStoriesActor
import pl.project13.tinytermpm.util.Preferences
import pl.project13.tinytermpm.api.tinypm.model.UserStory
import pl.project13.tinytermpm.api.harvest.DailyTimerApi

class DailyTimerActorTest extends FlatSpec with ShouldMatchers
  with BeforeAndAfterAll {

  val daily = TypedActor.newInstance(classOf[DailyTimerApi], new DailyTimerActor(Preferences), (10 seconds).toMillis)

  behavior of "DailyTimerActor"

  it should "get times from harvest" in {
    // given

    // when
    daily.timeToday()

    // then
  }

  override def afterAll() {
    TypedActor.stop(daily)
  }
}