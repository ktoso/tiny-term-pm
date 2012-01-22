package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty

@XmlRootElement
class Project {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var active: Boolean = false
  @BeanProperty
  var code: String = null
  @BeanProperty
  var name: String = null
  
  override def toString =
    "%d [code: %s] - %s".format(id, code, name)
}