package pl.project13.tinytermpm.cli.util

/**
 * A collection of ansi color codes, to make it useful in loggers etc
 */
object ColorizedStrings {

  import Console._

  def green(s: String) = GREEN + s + RESET
  implicit def s2green(s: String) = new { def green = ColorizedStrings.green(s) }

  def red(s: String) = RED + s + RESET
  implicit def s2red(s: String) = new { def red = ColorizedStrings.red(s) }

  def yellow(s: String) = YELLOW + s + RESET
  implicit def s2yellow(s: String) = new { def yellow = ColorizedStrings.yellow(s) }

  def b(s: String) = BOLD + s + RESET
  def bold(s: String) = b(s)
  implicit def s2b(s: String) = new { def b = ColorizedStrings.b(s) }
  implicit def s2bold(s: String) = new { def bold = ColorizedStrings.b(s) }
}