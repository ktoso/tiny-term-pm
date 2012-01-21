package pl.project13.tinytermpm.cli.parsing

import command._
import util.parsing.combinator.JavaTokenParsers

class CommandParser extends JavaTokenParsers {

  def positiveNumber: Parser[Int] =
    """\d+""".r ^^ { n => n.toInt }
  
  def tasks: Parser[ApiCommand] = ("tasks") ^^ {
    tasks => TasksCommand()
  }

  def users: Parser[ApiCommand] = ("users") ^^ {
    tasks => UsersCommand()
  }

  def stories: Parser[ApiCommand] = ("s" | "stories") ^^ {
    tasks => StoriesCommand()
  }
  
  def exit: Parser[ApiCommand] = ("wq" | "q" | "exit" | "quit") ^^ {
    exit => ExitCommand()
  }
  
  def iam: Parser[ApiCommand] = ("i am" | "self") ~> positiveNumber ^^ {
    id => SetSelfIdCommand(id) 
  }

  def create: Parser[ApiCommand] = ("c" | "create") ~ opt(createWhat) ^^ {
    case c ~ optWhat =>
      optWhat match {
        case Some("task") => CreateTaskCommand()
        case Some("story") => CreateStoryCommand()
        case None => CreateCommand()
      }
  }

  def createWhat: Parser[String] = "task" | "story"
  
  def command: Parser[ApiCommand] = (
    tasks 
  | users 
  | stories 
  | create 
  | iam
  | exit
  )
}
object CommandParser extends CommandParser {
  def parse(protoContent: String): ApiCommand = {
    parseAll(command, protoContent) match {
      case Success(res, _) => res.asInstanceOf[ApiCommand]
      case x: Failure => UnknownCommand()
      case x: Error => throw new RuntimeException(x.toString)
    }
  }
}