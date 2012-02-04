package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty
import java.util.{ArrayList, Collections, List}


@XmlRootElement
class Task {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = ""
  @BeanProperty
  var description: String = ""
  @BeanProperty
  var assignedUsers: List[User] = new ArrayList[User]
  @BeanProperty
  var comments: List[Comment] = new ArrayList[Comment]

  @BeanProperty
  var status: TaskStatus = _

  override def toString = "[id = %s, name = %s, desc = %s, assignedUsers = %s, comments = %s, status = %s]".format(id, name, description, assignedUsers, comments, status)
}