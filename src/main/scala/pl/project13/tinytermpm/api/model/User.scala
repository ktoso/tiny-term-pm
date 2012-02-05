package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty

@XmlRootElement
class User {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = null
  @BeanProperty
  var login: String = null
  @BeanProperty
  var email: String = null
  @BeanProperty
  var active: Boolean = true
  @BeanProperty
  var initials: String = null
}