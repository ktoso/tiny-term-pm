package pl.project13.tinytermpm.api.model

import reflect.BeanProperty
import java.util.{ArrayList, Collections, List}
import javax.xml.bind.annotation.{XmlElement, XmlElementWrapper, XmlRootElement}


@XmlRootElement
class Task {

  @BeanProperty
  var id: Long = 0

  @BeanProperty
  var name: String = ""

  @BeanProperty
  var description: String = ""

  @XmlElementWrapper(name = "users")
  @XmlElement(name = "user")
  var assignedUsers: List[User] = new ArrayList[User]

  @XmlElementWrapper(name = "comments")
  @XmlElement(name = "comment")
  var comments: List[Comment] = new ArrayList[Comment]

  @BeanProperty
  var status: TaskStatus = _

  override def toString = "[id = %s, name = %s, desc = %s, assignedUsers = %s, comments = %s, status = %s]".format(id, name, description, assignedUsers, comments, status)
}