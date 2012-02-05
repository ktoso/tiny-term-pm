package pl.project13.tinytermpm.cli.util

import org.joda.time.DateTime


case class PrettyDateTime(dt: DateTime, format: String = "hh:mm dd-mm-yyyy") {

  override val toString = dt.toString(format)

}
