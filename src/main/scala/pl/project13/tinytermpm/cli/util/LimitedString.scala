package pl.project13.tinytermpm.cli.util

/**
 * Creates a string representing either just the string,
 * or it "trimmed" to the max length.
 *
 * Examples:
 * {{{
 * LimitedString("konrad malawski", 9).toString == "konr[...]"
 * LimitedString("konrad malawski").toString == "konrad malawski"
 * }}}
 */
case class LimitedString(
  string: String,
  maxLength: Int = 0,
  trimMarker: String = "[...]") {

  override val toString = maxLength match {
    case 0 => string
    case maxLen if string.length > maxLen =>
      string.substring(0, maxLen - trimMarker.length) + trimMarker
    case _ => string
  }
    
}
