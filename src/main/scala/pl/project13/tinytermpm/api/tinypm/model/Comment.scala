package pl.project13.tinytermpm.api.tinypm.model

import javax.xml.bind.annotation.XmlRootElement
import reflect.BeanProperty
import org.joda.time.DateTime


@XmlRootElement
class Comment {

  @BeanProperty
  var author: String = ""
  @BeanProperty
  var date: DateTime = _
  @BeanProperty
  var body: String = ""

  override def toString = "[author = %s, date = %s, body = %s]".format(author, date, body)
}
