package pl.project13.tinytermpm.api.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.TasksApi
import pl.project13.tinytermpm.api.model.{UserStory, Task}
import pl.project13.tinytermpm.api.response.TasksResponse

class TasksActor(config: ApiPreferences) extends TypedActor with TasksApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def forUserStory(storyId: Long) = {
    val urlz = config.apiUrl("userstory"/storyId/"tasks")

    val response = h(url(urlz) as_str)

    val tasksResponse = JAXBUtil.unmarshal[TasksResponse](response)

    tasksResponse.getTasks
  }

  def createIn(story: UserStory, task: Task) = {
    0
  }

  def detailsFor(taskId: Long) = {
    val urlz = config.apiUrl("task"/taskId)

    try {
    val response = h(url(urlz) as_str)

    val task= JAXBUtil.unmarshal[Task](response)

    Some(task)
      
    } catch {
      case _ => None
    }
  }

  def update(task: Task) {

  }

  def delete(taskId: Long) {

  }
}