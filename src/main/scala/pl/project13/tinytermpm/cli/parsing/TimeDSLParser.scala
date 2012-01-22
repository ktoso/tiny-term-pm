package pl.project13.tinytermpm.cli.parsing

import time.TimeSpan
import util.parsing.combinator.JavaTokenParsers

class TimeDSLParser extends JavaTokenParsers {

  def positiveNumber: Parser[Int] =
    """\d+""".r ^^ { n => n.toInt }

  def minutes: Parser[TimeSpan] =
    positiveNumber <~ ("m" | " m" | "min" | " min" | "mins" | " mins" | "minutes" | " minutes") ^^ {
      case minutes => TimeSpan(0, minutes)
  }

  def hours: Parser[TimeSpan] =
    positiveNumber <~ ("h" | " h" | "hours" | "hours") ^^ {
      case hours => TimeSpan(hours)
  }
  
  def hoursMinutes: Parser[TimeSpan] = 
  hours ~ opt(" ") ~ minutes ^^ {
    case h ~ s ~ m => TimeSpan(h.hours, m.minutes)
  }

//  def from // from 8 till now
//  def day / days

  def percent: Parser[TimeSpan] =
    positiveNumber <~ ("%" | " %" | "p" | " p" | "proc" | " proc") ^^ {
      case workDayPercent => TimeSpan((8.0 / workDayPercent).toInt)
  }

  def time: Parser[TimeSpan] = (
    hours
  | minutes
  | hoursMinutes
  | percent
  )
}
object TimeDSLParser extends TimeDSLParser {
  def parse(protoContent: String): TimeSpan = {
    parseAll(time, protoContent) match {
      case Success(res: TimeSpan, _) => res
      case x: Failure => TimeSpan()
      case x: Error => throw new RuntimeException(x.toString)
    }
  }
}