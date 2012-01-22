package pl.project13.tinytermpm.cli.parsing.time

case class TimeSpan(hours: Int = 0, minutes: Int = 0) {
  implicit def toDouble = hours + minutes / 60.0
}