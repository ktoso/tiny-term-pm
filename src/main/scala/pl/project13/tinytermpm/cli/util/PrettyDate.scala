package pl.project13.tinytermpm.cli.util

import org.joda.time.DateTime


case class PrettyDate(dt: DateTime, format: String = "dd-mm-yyyy") {

  override val toString = dt.toString(format)

}
