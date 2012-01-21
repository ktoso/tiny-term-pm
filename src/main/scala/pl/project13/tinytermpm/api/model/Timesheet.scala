package pl.project13.tinytermpm.api.model

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty
import java.util.{ArrayList, List}

@XmlRootElement
class Timesheet {

  @BeanProperty
  @XmlElement(name = "activity")
  var timesheet: List[Activity] = new ArrayList[Activity]
}