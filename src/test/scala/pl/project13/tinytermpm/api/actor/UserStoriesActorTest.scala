package pl.project13.tinytermpm.api.actor

import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import akka.actor.TypedActor
import pl.project13.tinytermpm.api.UserStoriesApi
import pl.project13.tinytermpm.util.{Constants, Preferences}
import akka.actor.TypedActor
import akka.util.duration._
import pl.project13.tinytermpm.api.model.UserStory

class UserStoriesActorTest extends FlatSpec with ShouldMatchers {
  
  val stories = TypedActor.newInstance(classOf[UserStoriesApi], new UserStoriesActor(Preferences), (10 seconds).toMillis)
  
  behavior of "UserStoriesActor"
  
  it should "create and delete a userstory" in {
    // given
    val name = "Some Name" + System.currentTimeMillis()
    val story = new UserStory()
    story.setName(name)

    // when
    stories.create(story)

    // then
    val all = stories.forProject(Preferences.ProjectId)
    all.map { _.name } should contain (name)

    // cleanup
    val id = all.find { _.name == name }.get.id
    stories.deleteStory(id)
  }
}