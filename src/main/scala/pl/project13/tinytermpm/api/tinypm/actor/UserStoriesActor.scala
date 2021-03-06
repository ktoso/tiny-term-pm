package pl.project13.tinytermpm.api.tinypm.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.api.tinypm.UserStoriesApi
import org.joda.time.DateTime
import pl.project13.tinytermpm.api.response.{UserStoriesResponse, TasksResponse}
import pl.project13.tinytermpm.util.{Preferences, ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.HttpDispatch
import pl.project13.tinytermpm.api.tinypm.model.{UserStory, Status, Priority}

class UserStoriesActor(config: ApiPreferences) extends TypedActor with UserStoriesApi
  with HttpDispatch
  with ScalaJConversions {

  import pl.project13.scala.dispatch.fixes._
  import dispatch._

  def forProject(projectId: Long, ownerId: Option[Long] = None,
                 priority: Option[Priority] = None,
                 status: Option[Status] = None, createdAtFrom: Option[DateTime] = None,
                 createdAtTo: Option[DateTime] = None) = {
    val urlz = config.apiUrl("project" / projectId / "userstories")

    val response = h(url(urlz) as_str)

    val tasksResponse = JAXBUtil.unmarshal[UserStoriesResponse](response)

    tasksResponse.getUserStories
  }

  def forCurrentIterationIn(projectId: Long) = {
    val urlz = config.apiUrl("project"/projectId/"iteration/current/userstories")

    val response = h(url(urlz) as_str)

    val tasksResponse = JAXBUtil.unmarshal[UserStoriesResponse](response)

    tasksResponse.getUserStories
  }
  
  def deleteStory(userStory: Long) {
    val urlz = config.apiUrl("userstory"/userStory)
    
    h(url(urlz).DELETE as_str)
  }

  def forIteration(iterationId: Long) = null

  def detailsFor(userStoryId: Long) = {
    val urlz = config.apiUrl("userstory"/userStoryId)
    
    try {
      val response = h(url(urlz) as_str)
      val detailsResponse = JAXBUtil.unmarshal[UserStory](response)

      Some(detailsResponse)
    } catch {
      case e => None
    }
  }

  def update(userStory: UserStory) = null

  def create(story: UserStory, addDefaultTasks: Boolean = false) {
    val urlz = config.apiUrl("project"/Preferences.ProjectId/"userstories?addDefaultTasks="+addDefaultTasks)

    val body = JAXBUtil.marshal(story)
    h(url(urlz) << (body, XML) as_str)
  }
}