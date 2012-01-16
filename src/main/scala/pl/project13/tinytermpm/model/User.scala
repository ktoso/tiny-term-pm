package pl.project13.tinytermpm.model

import xml.{NodeSeq, XML}

case class User(active: Boolean, name: String, id: Int)

object User {
  def apply(xml: String): User = apply(XML.loadString(xml))

  def apply(xml: NodeSeq): User = xml match {
    case <user>{vals @ _*}</user> =>
      val active = (vals \\ "active").text.toBoolean
      val name = (vals \\ "name").text
      val id = (vals \\ "id").text.toInt

      User(active, name, id)
    case _ =>
      throw new RuntimeException("Unable to parse user...")
  }
}

object Users extends ContainerExtractor[User] {
  val elementName = "user"
  val elementType: PartialFunction[NodeSeq, User] = { case i: NodeSeq => User(i) }
}