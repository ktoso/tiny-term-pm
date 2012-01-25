package pl.project13.tinytermpm.cli.util

/**
 * A collection of ansi color codes, to make it useful in loggers etc
 */
object ColorizedStrings {

  import Console._

  def green(s: String) = GREEN + s + RESET
  implicit def s2green(s: String) = new { def green(s: String) = ColorizedStrings.green(s) }

  def red(s: String) = RED + s + RESET
  implicit def s2red(s: String) = new { def red(s: String) = ColorizedStrings.red(s) }

  def yellow(s: String) = YELLOW + s + RESET
  implicit def s2yellow(s: String) = new { def yellow(s: String) = ColorizedStrings.yellow(s) }

  def bold(s: String) = b(s)
  implicit def s2bold(s: String) = new { def bold(s: String) = ColorizedStrings.bold(s) }

  def b(s: String) = BOLD + s + RESET
  implicit def s2b(s: String) = new { def b(s: String) = ColorizedStrings.b(s) }
}