package pl.project13.tinytermpm.cli.parsing

import command._
import util.parsing.combinator.JavaTokenParsers
import scala.Predef._
import pl.project13.tinytermpm.util.Preferences
import org.joda.time.DateTime

class CommandParser extends JavaTokenParsers with CombinedParsers {

  val positiveNumber: Parser[Int] =
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

  def projectDetails: Parser[ApiCommand] = "project " ~> opt(positiveNumber) ^^ {
    case _id => _id match {
      case None => ProjectsCommand(Some(Preferences.ProjectId))
      case Some(id) => ProjectsCommand(Some(id))
    }
  }

  def stories: Parser[ApiCommand] = ("userstories" | "stories") ^^ {
    case stories => StoriesCommand()
  }

  def taskDetails: Parser[ApiCommand] = ("task " | "t ") ~> positiveNumber ^^ {
    case id => TaskDetailsCommand(id)
  }

  def storyDetails: Parser[ApiCommand] = ("userstory " | "story ") ~> positiveNumber ^^ {
    case id => StoriesCommand(Some(id))
  }
  
  def exit: Parser[ApiCommand] = ("wq" | "q" | "exit" | "quit") ^^ {
    case exit => ExitCommand()
  }

  def iterations: Parser[ApiCommand] = ("iterations" | "i") ^^ {
    case exit => IterationsCommand()
  }
  
  def iam: Parser[ApiCommand] = ("user " | "i am " | "self ") ~> positiveNumber ^^ {
    case id => SetSelfIdCommand(id)
  }

  def createTask: Parser[ApiCommand] = combinedParser("create", "c")("task") ^^ {
    case c => CreateTaskCommand()
  }

  def whoAmI: Parser[ApiCommand] = "whoami" ^^ {
    case _ => WhoAmICommand()
  }

  def timeTodayShorthand: Parser[ApiCommand] = "tt" ^^ {
    case _ => TimeOnDayHarvestCommand(new DateTime)
  }

  def timeToday: Parser[ApiCommand] = ("time" | "t") ~> ".*".r ^^ {
    case "today" | "t" => TimeOnDayHarvestCommand(new DateTime)
    case "yesterday" | "y" => TimeOnDayHarvestCommand((new DateTime).minusDays(1))
    case "week" | "w" =>
      val now = new DateTime
      TimeOnDaysHarvestCommand(now.withDayOfWeek(1), now)
    case "month" | "m" =>
      val now = new DateTime
      TimeOnDaysHarvestCommand(now.withDayOfMonth(1), now)
    case "year" | "y" =>
      val now = new DateTime
      TimeOnDaysHarvestCommand(now.withDayOfMonth(1).withMonthOfYear(1), now)
    case desc => throw new RuntimeException("Unable to parse timespan descriptor: [" + desc + "]")
  }
    
  def createStory: Parser[ApiCommand] = combinedParser("create", "c")("story") ^^ {
    case c => CreateStoryCommand()
  }

  def deleteStory: Parser[ApiCommand] = combinedParser("delete", "d")("story") ~> positiveNumber ^^ {
    case id => DeleteStoryCommand(id)
  }

  def deleteTasks: Parser[ApiCommand] = combinedParser("delete", "d")("task") ~> repsep(positiveNumber, ",") ^^ {
    case ids => DeleteTasksCommand(ids.map(_.toLong))
  }

  def nothing: Parser[ApiCommand] = "" ^^ {
    case it => NoOpCommand()
  }

  def command: Parser[ApiCommand] = (
    createStory
  | deleteStory
  | createTask
  | deleteTasks
  | iterations
  | tasks
  | timeToday
  | taskDetails
  | whoAmI
  | storyDetails
  | stories
  | users
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
    try {
      parseAll(command, content.trim) match {
        case Success(res: ApiCommand, _) => res
        case x: Failure => UnknownCommand(content)
        case x: Error => throw new RuntimeException(x.toString)
      }
    } catch {
      case e => println("Cought exception: " + e.getMessage)
      NoOpCommand()
    }
  }
}