package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty

@XmlRootElement
class Task {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = null
  @BeanProperty
  var status: Status = null
}