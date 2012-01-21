package pl.project13.tinytermpm.api

import model.User
import pl.project13.tinytermpm.util.PathConversions._
import response.UsersResponse
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}

trait UsersApi {
  def allUsers(): List[User]
  def detailsFor(id: Int): User
}

class UsersActor(config: ApiPreferences) extends TypedActor with UsersApi
 with ScalaJConversions {

  import dispatch._
  val h = new Http
  
  def allUsers() = {
    val urlz = config.apiUrl("users")

    val response = h(url(urlz) as_str)

    val users= JAXBUtil.unmarshal(classOf[UsersResponse], response)

    users.getUsers
  }

  def detailsFor(id: Int) = {
    val urlz = config.apiUrl("user" / id)

    val response = h(url(urlz) as_str)

    val user = JAXBUtil.unmarshal(classOf[User], response)

    user
  }

  override def postStop() {
    h.shutdown()
  }
}