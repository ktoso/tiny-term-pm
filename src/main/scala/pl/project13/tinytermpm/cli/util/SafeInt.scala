package pl.project13.tinytermpm.cli.util

object SafeInt {
  val Number = "[0-9]+".r

  def apply(s: String) = s match {
    case Number => s.toInt
    case _ => 0
  }
}