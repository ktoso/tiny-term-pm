package pl.project13.tinytermpm.api.tinypm.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.response.UsersResponse
import pl.project13.tinytermpm.api.tinypm.model.Activity
import pl.project13.tinytermpm.api.tinypm.TimeSheetApi
import pl.project13.tinytermpm.api.HttpDispatch

class TimeSheetActor(config: ApiPreferences) extends TypedActor with TimeSheetApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def allUsers() = {
    val urlz = config.apiUrl("users")

    val response = h(url(urlz) as_str)

    val users = JAXBUtil.unmarshal[UsersResponse](response)

    users.getUsers
  }

  def forCurrentIteration(userId: Option[Long]) = null

  def forIteration(iterationId: Long, userId: Option[Long]) = null

  def forTask(taskId: Long, userId: Option[Long]) = null

  def create(taskId: Long, activity: Activity) {
    val urlz = config.apiUrl("task" / taskId / "timesheet")

    val activityJson = JAXBUtil.marshal(activity)

    val response = h(url(urlz) << activityJson as_str)

    val createdActivity = JAXBUtil.unmarshal[Activity](response)
  }

  def delete(taskId: Long, activity: Activity) {
    activity.timeSpent = 0
    create(taskId, activity)
  }
}