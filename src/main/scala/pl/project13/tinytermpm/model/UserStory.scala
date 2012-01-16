package pl.project13.tinytermpm.model

import xml.{NodeSeq, XML}

case class UserStory(id: Long, name: String, iterationId: Long,  position: Long, tags: List[Tag])
object UserStory {
  
  def apply(xml: String): UserStory = apply(XML.loadString(xml))

    def apply(xml: NodeSeq): UserStory = xml match {
      case <userStory>{vals @ _*}</userStory> =>
        val id = (vals \\ "id").text.toLong
        val iterationId = (vals \\ "iterationId").text.toLong
        val position = (vals \\ "position").text.toLong
        val tags = Tags(vals \\ "position")
        val name = (vals \\ "name").text

        UserStory(id, name, iterationId, position, tags)
      case _ =>
        throw new RuntimeException("Unable to parse user...")
    }
}
object UserStories extends ContainerExtractor[UserStory] {
  val elementName = "userStory"
  val elementType: PartialFunction[NodeSeq, UserStory] = { case i: NodeSeq => UserStory(i) }
}