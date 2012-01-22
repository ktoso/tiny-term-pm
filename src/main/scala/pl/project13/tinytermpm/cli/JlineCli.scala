package pl.project13.tinytermpm.cli

import completion.CommandsCompletor
import jline.{CandidateListCompletionHandler, ConsoleReader}


trait Cli {
  def tel(message: Any)
  def tell(message: Any)
  def warn(message: Any)
  def shell(): String
  def askFor(message: Any): String
}

class JlineCli extends Cli {

  val reader = new ConsoleReader
  reader.setDefaultPrompt("> ");
  reader.setUseHistory(true);
  reader.setCompletionHandler(new CandidateListCompletionHandler());
  reader.addCompletor(new CommandsCompletor)

  def shell() = {
    reader.readLine()
  }

  def askFor(message: Any) = {
    Console.print(message + " ")
    Console.readLine()
  }

  def tel(message: Any) {
    Console.print(message)
  }

  def tell(message: Any) {
    Console.println(message)
  }

  def warn(message: Any) {
    Console.println(message)
  }
}