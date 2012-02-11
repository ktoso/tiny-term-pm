package pl.project13.tinytermpm.cli.parsing.time

case class TimeSpan(hours: Int = 0, minutes: Int = 0) {
  implicit def toDouble = hours + minutes / 60.0

  override val toString = "%sh %sm".format(hours, minutes)
}
object TimeSpan {
  def apply(hours: Double): TimeSpan = {
    val h= hours.toInt
    val m = ((hours - h) * 60).toInt
    
    TimeSpan(h, m)
  } 
}