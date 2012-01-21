package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlRootElement
import java.util.Date
import reflect.BeanProperty

@XmlRootElement
class Activity {

  @BeanProperty
  var date: Date = null
  @BeanProperty
  var task: Task = null
  @BeanProperty
  var timeSpent: Double = 0
  @BeanProperty
  var user: User = null
  @BeanProperty
  var userStory: UserStory = null
}