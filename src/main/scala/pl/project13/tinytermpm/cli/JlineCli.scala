package pl.project13.tinytermpm.cli

import completion.CommandsCompletor
import jline.{CandidateListCompletionHandler, ConsoleReader}
import pl.project13.tinytermpm.util.verb.Quittable
import util.SafeBoolean
import util.ColorizedStrings._


trait Cli {
  def tel(message: Any)
  def tel(message: String, interpolations: Any*)

  def tell(message: Any)
  def tell(message: String, interpolations: Any*)

  def warn(message: Any)
  def warn(message: String, interpolations: Any*)

  def err(message: Any)
  def err(message: String, interpolations: Any*)

  def shell(): String

  def askFor(message: Any): String
  def askForBoolean(message: Any, defaultValue: Boolean): Boolean
  def askOrQuit(message: Any): String
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

  def askFor(message: Any) = {
    Console.print(message + " ")
    Console.readLine()
  }
  
  def askForBoolean(message: Any, defaultValue: Boolean = false) = {
    SafeBoolean(askFor(message), defaultValue)
  }
  
  def askOrQuit(message: Any) = {
    val line = askFor(message)
    
    if(Quittable.Triggers.contains(line)) throw new Quittable.Quit()

    line
  }

  def tel(message: Any) {
    Console.print(message)
  }

  def tel(message: String, interpolations: Any*) {
    Console.print(message.format(interpolations: _*))
  }

  def tell(message: Any) {
    Console.println(message)
  }

  def tell(message: String, interpolations: Any*) {
    Console.println(message.format(interpolations: _*))
  }

  def warn(message: Any) {
    Console.println(message.toString.yellow)
  }
  
  def warn(message: String, interpolations: Any*) {
    Console.println(message.toString.format(interpolations: _*).yellow)
  }

  def err(message: Any) {
    Console.println(message.toString.red)
  }

  def err(message: String, interpolations: Any*) {
    Console.println(message.toString.format(interpolations: _*).red)
  }
}