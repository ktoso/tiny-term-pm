package pl.project13.tinytermpm.api.tinypm.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.tinypm.ProjectsApi
import pl.project13.tinytermpm.api.response.ProjectsResponse
import pl.project13.tinytermpm.api.HttpDispatch
import pl.project13.tinytermpm.api.tinypm.model.Project

class ProjectsActor(config: ApiPreferences) extends TypedActor with ProjectsApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def all() = {
    val urlz = config.apiUrl("projects")

    val response = h(url(urlz) as_str)

    val projectsResponse= JAXBUtil.unmarshal[ProjectsResponse](response)

    projectsResponse.getProjects
  }

  def detailsFor(id: Long) = {
    val urlz = config.apiUrl("project" / id)

    val response = h(url(urlz) as_str)

    val project= JAXBUtil.unmarshal[Project](response)

    project
  }

}