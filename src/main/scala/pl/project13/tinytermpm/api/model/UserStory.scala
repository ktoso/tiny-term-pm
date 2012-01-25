package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement
import java.util.{ArrayList, Collections, List}
import reflect.BeanProperty

@XmlRootElement
class UserStory {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = null
  @BeanProperty
  var iterationId: Long = 0
  @BeanProperty
  var position: Long = 0

  @XmlElementWrapper(name = "tags")
  @XmlElement(name = "tag")
  var tags: List[String] = new ArrayList[String]
}