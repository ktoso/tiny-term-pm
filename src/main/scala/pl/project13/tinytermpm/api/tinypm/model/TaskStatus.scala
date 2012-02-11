package pl.project13.tinytermpm.api.tinypm.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty
import pl.project13.tinytermpm.cli.util.ColorizedStrings._


@XmlRootElement(name = "status")
class TaskStatus {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = ""

  def coloredName = name match {
    case "IN PROGRESS" => name.yellow
    case "COMPLETED" => name.green
    case _ => name
  }

  override def toString = "[id = %s, name = %s]".format(id, name)
}