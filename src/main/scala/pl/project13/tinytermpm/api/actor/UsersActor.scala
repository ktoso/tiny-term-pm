package pl.project13.tinytermpm.api.actor

import pl.project13.tinytermpm.util.PathConversions._
import akka.actor.TypedActor
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.util.{ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.UsersApi
import pl.project13.tinytermpm.api.response.UsersResponse
import pl.project13.tinytermpm.api.model.User

class UsersActor(config: ApiPreferences) extends TypedActor with UsersApi
 with HttpDispatch with ScalaJConversions {

  import dispatch._

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

}