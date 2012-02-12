package pl.project13.tinytermpm.cli

import completion.CommandsCompletor
import jline.{CandidateListCompletionHandler, ConsoleReader}
import pl.project13.tinytermpm.util.verb.Quittable
import util.SafeBoolean
import util.ColorizedStrings._
import annotation.tailrec


trait Cli {
  def tel(message: => Any)
  def tel(message: => String, interpolations: Any*)

  def tell(message: => Any)
  def tell(message: => String, interpolations: Any*)

  def warn(message: => Any)
  def warn(message: => String, interpolations: Any*)

  def err(message: => Any)
  def err(message: => String, interpolations: Any*)

  def shell(): String

  def askFor(message: => Any): String
  def askForHidden(message: => Any): String
  def askOrQuit(message: => Any): String

  def askForLongSelection(message: => Any, acceptable: List[Long], defaultVal: Option[Long] = None): Long =
    askForSelection(message, acceptable.map(_.toString)).toLong
  def askForIntSelection(message: => Any, acceptable: List[Int], defaultVal: Option[Int] = None): Int =
    askForSelection(message, acceptable.map(_.toString)).toInt
  def askForSelection(message: => Any, acceptable: List[String], defaultVal: Option[String] = None): String

  def askForLongSelectionOrQuit(message: => Any, acceptable: List[Long], defaultVal: Option[Long] = None): Option[Long] =
    askForSelectionOrQuit(message, acceptable.map(_.toString)).flatMap { s => Some(s.toLong) }
  def askForIntSelectionOrQuit(message: => Any, acceptable: List[Int], defaultVal: Option[Int] = None): Option[Int] =
    askForSelectionOrQuit(message, acceptable.map(_.toString)).flatMap { s => Some(s.toInt) }
  def askForSelectionOrQuit(message: => Any, acceptable: List[String], defaultVal: Option[String] = None): Option[String]

  def askForBoolean(message: => Any, defaultValue: Boolean): Boolean

}

class JlineCli extends Cli {

  val reader = new ConsoleReader
  reader.setDefaultPrompt("> ");
  reader.setUseHistory(true);
  reader.setCompletionHandler(new CandidateListCompletionHandler());
  reader.addCompletor(new CommandsCompletor)

  val w = 80

  def shell() = {
    reader.readLine()
  }

  def askFor(message: => Any) =
    reader.readLine(message + " ")

  def askForHidden(message: => Any) =
    reader.readLine(message + " ", '*')

  @tailrec
  final def askForSelection(message: => Any, acceptable: List[String], defaultVal: Option[String] = None): String = {
    val response = askFor(message)

    if(response == "" && defaultVal.isDefined) defaultVal.get
    else if(acceptable.contains(response)) response
    else askForSelection(message, acceptable)
  }

  @tailrec
  final def askForSelectionOrQuit(message: => Any, acceptable: List[String], defaultVal: Option[String] = None): Option[String] = {
    val response = askFor(message)

    if(response == "q") None
    else if(response == "" && defaultVal.isDefined) defaultVal
    else if(acceptable.contains(response)) Some(response)
    else askForSelectionOrQuit(message, acceptable)
  }
  
  def askForBoolean(message: => Any, defaultValue: Boolean = false) = defaultValue match {
    case true => SafeBoolean(askFor(message+ " [Y/n] "), defaultValue)
    case false => SafeBoolean(askFor(message+ " [y/N] "), defaultValue)
  }

  def askOrQuit(message: => Any) = {
    val line = askFor(message)
    
    if(Quittable.Triggers.contains(line)) throw new Quittable.Quit()

    line
  }

  def tel(message: => Any) {
    Console.print(message)
  }

  def tel(message: => String, interpolations: Any*) {
    Console.print(message.format(interpolations: _*))
  }

  def tell(message: => Any) {
    Console.println(message)
  }

  def tell(message: => String, interpolations: Any*) {
    Console.println(message.format(interpolations: _*))
  }

  def warn(message: => Any) {
    Console.println(message.toString.yellow)
  }
  
  def warn(message: => String, interpolations: Any*) {
    Console.println(message.toString.format(interpolations: _*).yellow)
  }

  def err(message: => Any) {
    Console.println(message.toString.red)
  }

  def err(message: => String, interpolations: Any*) {
    Console.println(message.toString.format(interpolations: _*).red)
  }
}