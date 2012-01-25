package pl.project13.tinytermpm.api.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.{UserStoriesApi, TasksApi}
import pl.project13.tinytermpm.api.model.{Status, Priority, UserStory, Task}
import org.joda.time.DateTime
import pl.project13.tinytermpm.api.response.{UserStoriesResponse, TasksResponse}

class UserStoriesActor(config: ApiPreferences) extends TypedActor with UserStoriesApi
with HttpDispatch with ScalaJConversions {

  import dispatch._

  def forProject(projectId: Long, ownerId: Option[Long] = None, priority: Option[Priority] = None,
                 status: Option[Status] = None, createdAtFrom: Option[DateTime] = None,
                 createdAtTo: Option[DateTime] = None) = {
    val urlz = config.apiUrl("project" / projectId / "userstories")

    val response = h(url(urlz) as_str)

    val tasksResponse = JAXBUtil.unmarshal(classOf[UserStoriesResponse], response)

    tasksResponse.getUserStories
  }

  def forCurrentIterationIn(projectId: Long) = {
    val urlz = config.apiUrl("project" / projectId / "iteration/current/userstories")

    val response = h(url(urlz) as_str)

    val tasksResponse = JAXBUtil.unmarshal(classOf[UserStoriesResponse], response)

    tasksResponse.getUserStories
  }

  def forIteration(iterationId: Long) = null

  def detailsFor(userStoryId: Long) = null

  def update(userStory: UserStory) = null

  def createInCurrentIteration(story: UserStory) = {
    0
  }

  def create(story: UserStory) = {
    0
  }
}