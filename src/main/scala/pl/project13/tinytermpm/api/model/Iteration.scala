package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import java.util.{ArrayList, List}
import reflect.BeanProperty
import org.joda.time.DateTime

@XmlRootElement
class Iteration {

  @BeanProperty
  var id: Long = 0
  @BeanProperty
  var name: String = ""
  @BeanProperty
  var goal: String = ""
  @BeanProperty
  var plannedVelocity: Double = 0
  @BeanProperty
  var position: Long = 0

  @BeanProperty
  var startDate: DateTime = null // 2006-04-01

  @BeanProperty
  def endDate = startDate.plusDays(duration)  // calculated

  /**
   * Iteration duration in days
   */
  @BeanProperty
  var duration: Int = 0

  @XmlElement(name = "activity")
  var activities: List[Activity] = new ArrayList[Activity]
}