package pl.project13.tinytermpm.cli.util

object SafeBoolean {
  def apply(s: String, defaultValue: Boolean = false) = s.toLowerCase match {
    case "true" | "false" => s.toLowerCase.toBoolean
    case "1" | "y" | "yes" => true
    case "0" | "n" | "no" => false
    case _ => defaultValue
  }
}