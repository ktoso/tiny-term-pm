package pl.project13.tinytermpm

import annotation.tailrec
import api.{UsersActor, UsersApi}
import cli.Cli
import cli.parsing.command.{SetSelfIdCommand, ExitCommand, TasksCommand, UsersCommand}
import cli.parsing.CommandParser
import akka.actor.TypedActor
import akka.util.duration._
import util.Preferences

class Repl(cli: Cli) {
  import cli._

  tell("Loading REPL...")

  var exitRepl = false

  val users = TypedActor.newInstance(classOf[UsersApi], new UsersActor(Preferences), (10 seconds).toMillis)

  tell("Done!")
  tell("")

  @tailrec
  final def start() {
    val cmd = shell(">")

    CommandParser.parse(cmd) match {
      case UsersCommand() =>
        val allUsers = users.allUsers()
        allUsers.filter(_.active).foreach {u =>
          import u._
          println("%d: %s (%s)".format(id, name, email))
        }

      case SetSelfIdCommand(id) =>
        val user = users.detailsFor(id)
        Preferences.saveUserDetails(id, user.name)

        tell("Hello, "+user.name+"!")
        
      case TasksCommand() =>
        tell("Tasks...")
        
      case ExitCommand() =>
        TypedActor.stop(users)
        exitRepl = true
        
      case _ =>
        tell("Unknown command... Type [help] for a list of available commands.")
    }

    if (!exitRepl) start()
  }
}