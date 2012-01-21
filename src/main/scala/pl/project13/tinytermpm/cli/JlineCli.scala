package pl.project13.tinytermpm.cli

import completion.CommandsCompletor
import jline.{CandidateListCompletionHandler, ConsoleReader}


trait Cli {
  def tel(message: String)
  def tell(message: String)
  def warn(message: String)
  def shell(message: String): String
  def askFor(message: String): String
}

class JlineCli extends Cli {

  val reader = new ConsoleReader
  reader.setDefaultPrompt("> ");
  reader.setUseHistory(true);
  reader.setCompletionHandler(new CandidateListCompletionHandler());
  reader.addCompletor(new CommandsCompletor)

  def shell(message: String) = {
    reader.readLine()
  }

  def askFor(message: String) = {
    Console.print(message + " ")
    Console.readLine()
  }

  def tel(message: String) {
    Console.print(message)
  }

  def tell(message: String) {
    Console.println(message)
  }

  def warn(message: String) {
    Console.println(message)
  }
}