package pl.project13.tinytermpm.api.model

import reflect.BeanProperty
import javax.xml.bind.annotation.{XmlElement, XmlElementWrapper, XmlRootElement}
import java.util.{ArrayList, Collections}

@XmlRootElement
class Project {

  @BeanProperty
  var id: Long = 0

  @BeanProperty
  var name: String = ""

  @BeanProperty
  var description: String = ""

  @BeanProperty
  var code: String = ""

  @BeanProperty
  var active: Boolean = false

  @BeanProperty
  var owner: User = _
  
  @XmlElementWrapper(name = "taskStatuses")
  @XmlElement(name = "status")
  var taskStatuses: java.util.List[TaskStatus] = new ArrayList[TaskStatus]

  @XmlElementWrapper(name = "taskEstimates")
  @XmlElement(name = "value")
  var taskEstimates: java.util.List[Double] = new ArrayList[Double]

  
  override def toString =
    "%d [code: %s] - %s".format(id, code, name)
}