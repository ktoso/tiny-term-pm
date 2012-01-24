package pl.project13.tinytermpm

import annotation.tailrec
import api._
import api.actor._
import cli.Cli
import cli.parsing.command._
import cli.parsing.CommandParser
import akka.actor.TypedActor
import akka.util.duration._
import util.{Constants, Preferences}
import cli.util.AnsiCodes._

class Repl(cli: Cli) {
  import cli._

  tell("Loading REPL...")

  var exitRepl = false

  val users = TypedActor.newInstance(classOf[UsersApi], new UsersActor(Preferences), (10 seconds).toMillis)
  val projects = TypedActor.newInstance(classOf[ProjectsApi], new ProjectsActor(Preferences), (10 seconds).toMillis)

  tell("Done!")
  tell("")

  def doProjects(cmnd: ProjectsCommand) {
    cmnd.projectId match {
      case Some(id) =>
        val details = projects.detailsFor(id)
        tell(details.toString)

        Preferences.saveProject(details)
        tell("Will use "+b(details.name)+" as default project in future api calls...")

      case None =>
        val all = projects.all()
        
        all.foreach{ p => 
          import p._
          println("%d - %s".format(id, name))
        }
    }
  }

  def doTasks() {

  }

  def doHelp() {
    tell(Constants.HelpText)
  }

  def doAllUsers() {
    val allUsers = users.allUsers()
    allUsers.filter(_.active).foreach {
      u =>
        import u._
        println("%d: %s (%s)".format(id, name, email))
    }
  }

  def doSelfId(id: Int) {
    val user = users.detailsFor(id)
    Preferences.saveUserDetails(id, user.name)

    tell("Hello, " + user.name + "!")
  }

  def doExit() {
    (users :: projects :: Nil).foreach{ TypedActor.stop(_) }
    exitRepl = true
  }

  @tailrec
  final def start() {
    val cmd = shell()

    CommandParser.parse(cmd) match {
      case UsersCommand() => doAllUsers()
      case SetSelfIdCommand(id) => doSelfId(id)
      case TasksCommand() => doTasks()
      case c: ProjectsCommand => doProjects(c)

      case HelpCommand() => doHelp()
      case ExitCommand() => doExit()
        
      case NoOpCommand() => ;
      case unknown => tell("Unknown command... Type [help] for a list of available commands: " + unknown)
    }

    if (!exitRepl) start()
  }
}
