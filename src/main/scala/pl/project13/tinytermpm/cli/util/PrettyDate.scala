package pl.project13.tinytermpm.cli.util

import org.joda.time.DateTime


case class PrettyDate(dt: DateTime, format: String = "dd-MM-YYYY") {

  def withDayNamePrefix = PrettyDate(dt, "EEEE "+format)

  override val toString = dt.toString(format)

}
