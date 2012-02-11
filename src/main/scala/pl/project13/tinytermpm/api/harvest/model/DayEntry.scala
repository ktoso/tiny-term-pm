package pl.project13.tinytermpm.api.harvest.model

import org.joda.time.DateTime
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.XmlElement

@XmlRootElement(name = "day_entry")
class DayEntry {

  var id: Long = _
  
  @XmlElement(name = "spent_at")
  var spentAt: DateTime = _

  var client: String = ""

  @XmlElement(name = "user_id")
  var userId: Long = _

  @XmlElement(name = "project_id")
  var projectId: Long = _
  var project: String = ""

  @XmlElement(name = "task_id")
  var taskId: Long = _
  var task: String = ""

  var hours: Double = 0 
  
  @XmlElement(name = "started_at")
  var startedAt: String = "" // todo make this a time
  @XmlElement(name = "ended_at")
  var endedAt: String = "" // todo make this a time
}
