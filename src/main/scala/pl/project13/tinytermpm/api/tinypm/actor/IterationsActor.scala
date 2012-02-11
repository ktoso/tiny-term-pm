package pl.project13.tinytermpm.api.tinypm.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.tinypm.IterationsApi
import pl.project13.tinytermpm.api.response.{IterationsResponse, ProjectsResponse}
import org.joda.time.DateTime
import pl.project13.tinytermpm.api.HttpDispatch


class IterationsActor(config: ApiPreferences) extends TypedActor with IterationsApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def forProject(projectId: Long) = {
    val urlz = config.apiUrl("project" / projectId / "iterations")

    val rsp = h(url(urlz) as_str)

    val response = JAXBUtil.unmarshal[IterationsResponse](rsp)

    response._iterations
  }

  def currentForProject(projectId: Long) = {
    val iterations = forProject(projectId)

    iterations.find{ it => it.startDate.plusDays(it.duration.toInt).isAfterNow }
  }
}
