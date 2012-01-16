package pl.project13.tinytermpm.model

import xml.{NodeSeq, XML}
import Status._

/**
 * <task>
 *   <id>1</id>
 *   <name>Write decent documentation.</name>
 *   <!-- status: one of provided by the project resource on taskStatuses list -->
 *   <status>
 *     <id>2</id>
 *     <name>IN PROGRESS</name>
 *   </status>
 * </task>
 */
case class Task(id: Long,  name: String,  status: Status)

object Task {

  def apply(xml: String): Task = apply(XML.loadString(xml))

    def apply(xml: NodeSeq): Task = xml match {
      case <task>{vals @ _*}</task> =>
        val id = (vals \\ "id").text.toLong
        val name = (vals \\ "name").text
        val status = Status.withName((vals \\ "position").text)

        Task(id, name, status)
      case _ =>
        throw new RuntimeException("Unable to parse user...")
    }
}
