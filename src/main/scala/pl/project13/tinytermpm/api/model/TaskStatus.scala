package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty


@XmlRootElement(name = "status")
class TaskStatus {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = ""

  override def toString = "[id = %s, name = %s]".format(id, name)
}