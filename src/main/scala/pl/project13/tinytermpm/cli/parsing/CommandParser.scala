package pl.project13.tinytermpm.cli.parsing

import command._
import util.parsing.combinator.JavaTokenParsers
import scala.Predef._

class CommandParser extends JavaTokenParsers {

  def positiveNumber: Parser[Int] =
    """\d+""".r ^^ { n => n.toInt }
  
  def tasks: Parser[ApiCommand] = ("tasks") ^^ {
    case tasks => TasksCommand()
  }

  def help: Parser[ApiCommand] = ("help" | "h") ^^ {
    help => HelpCommand()
  }

  def users: Parser[ApiCommand] = "users" ^^ {
    case users => UsersCommand()
  }

  def projects: Parser[ApiCommand] = "projects" ^^ {
    case p => ProjectsCommand()
  }

  def projectDetails: Parser[ApiCommand] = "project" ~> positiveNumber ^^ {
    case id => ProjectsCommand(Some(id))
  }

  def stories: Parser[ApiCommand] = ("s" | "stories") ^^ {
    case tasks => StoriesCommand()
  }
  
  def exit: Parser[ApiCommand] = ("wq" | "q" | "exit" | "quit") ^^ {
    case exit => ExitCommand()
  }
  
  def iam: Parser[ApiCommand] = ("i am " | "self ") ~> positiveNumber ^^ {
    case id => SetSelfIdCommand(id)
  }

  def create: Parser[ApiCommand] = ("c" | "create") ~ opt(createWhat) ^^ {
    case c ~ optWhat =>
      optWhat match {
        case Some("task") => CreateTaskCommand()
        case Some("story") => CreateStoryCommand()
        case None => CreateCommand()
      }
  }
  
  def nothing: Parser[ApiCommand] = "" ^^ { case it => NoOpCommand() }

  def createWhat: Parser[String] = "task" | "story"
  
  def command: Parser[ApiCommand] = (
    tasks 
  | users 
  | stories 
  | create 
  | iam
  | exit
  | projects
  | projectDetails
  | help
  | nothing
  )
}
object CommandParser extends CommandParser {
  def parse(content: String): ApiCommand = {
    parseAll(command, content) match {
      case Success(res: ApiCommand, _) => res
      case x: Failure => UnknownCommand(content)
      case x: Error => throw new RuntimeException(x.toString)
    }
  }
}