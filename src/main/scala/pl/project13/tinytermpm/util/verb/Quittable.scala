package pl.project13.tinytermpm.util.verb

import pl.project13.tinytermpm.cli.Cli

object Quittable {

  val Triggers = "q" :: "q!" :: "quit" :: Nil

  def quittable(block: => Unit)(implicit cli: Cli) {
    try {
      block
    } catch {
      case q: Quit => cli.tell("Quitting...")
    }
  }

  class Quit extends RuntimeException
}

