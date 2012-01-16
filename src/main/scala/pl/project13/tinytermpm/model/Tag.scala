package pl.project13.tinytermpm.model

import xml.{XML, NodeSeq}
import scala.PartialFunction

case class Tag(name: String)
object Tag {
  def apply(xml: NodeSeq): Tag = xml match {
    case <tag>{tag @ _*}</tag> => Tag(tag)
    case _ => throw new RuntimeException("Unable to parse tag...")
  }
}
object Tags extends ContainerExtractor[Tag] {
  val elementName = "tag"
  val elementType: PartialFunction[NodeSeq, Tag] = { case i: NodeSeq => Tag(i) }
}