package pl.project13.tinytermpm

import annotation.tailrec
import api._
import api.actor._
import api.model.UserStory
import cli.Cli
import cli.parsing.command._
import cli.parsing.CommandParser
import akka.actor.TypedActor
import akka.util.duration._
import cli.util.SafeBoolean
import util.verb.Quittable._
import util.{Constants, Preferences}
import cli.util.ColorizedStrings._

class Repl(cli: Cli) {
  implicit val _cli = cli // for fluent quittable usage
  import cli._

  tell("Loading REPL...")

  var exitRepl = false

  val users = TypedActor.newInstance(classOf[UsersApi], new UsersActor(Preferences), (10 seconds).toMillis)
  val stories = TypedActor.newInstance(classOf[UserStoriesApi], new UserStoriesActor(Preferences), (10 seconds).toMillis)
  val projects = TypedActor.newInstance(classOf[ProjectsApi], new ProjectsActor(Preferences), (10 seconds).toMillis)

  tell("Done!")
  tell("")

  def doProjects(projectId: Option[Long]) {
    projectId match {
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
        println("%d: %s <%s>".format(id, name, email))
    }
  }

  def doUserStories() {
    val storiesInProject = stories.forProject(Preferences.ProjectId)

    tell(""+storiesInProject.size+" stories in project ["+Preferences.ProjectName.bold+"]")
    printStories(storiesInProject)
  }
  
  def doUserStoryDetails(id: Long) {
    val story = stories.detailsFor(id)
    
    tell("""|#%d - %s
            |[tags: %s]""".stripMargin.format(story.id, story.name.bold, story.tags))
  }

  def doSelfId(id: Int) {
    val user = users.detailsFor(id)
    Preferences.saveUserDetails(id, user.name)

    tell("Hello, " + user.name + "!")
  }

  def doCreateStory() {
    tell("Create user story in "+Preferences.ProjectName.bold+"... (enter single "+"q".bold+" to abort)")

    quittable {
      val story = new UserStory

      val name = askOrQuit("Story name:")
      story.setName(name)

      val addDefaultTasks = SafeBoolean(askOrQuit("Add default tasks [y/n]"))

      tell("Fire and forget, creating user story...")
      stories.create(story, addDefaultTasks)
    }
  }

  def doExit() {
    (users :: projects :: Nil).foreach{ TypedActor.stop(_) }
    exitRepl = true
  }

  private def printStories(stories: List[UserStory]) {
    stories.foreach { story =>
      tell("#%d - %s".format(story.getId, story.getName))
    }
  }

  @tailrec
  final def start() {
    val cmd = shell()

    CommandParser.parse(cmd) match {
      case UsersCommand() => doAllUsers()

      case SetSelfIdCommand(id) => doSelfId(id)

      case TasksCommand() => doTasks()

      case ProjectsCommand(id) => doProjects(id)

      case StoriesCommand(None) => doUserStories()
      case StoriesCommand(Some(id)) => doUserStoryDetails(id)
        
      case CreateStoryCommand() => doCreateStory()
      case CreateCommand() => println("implement do create")

      case HelpCommand() => doHelp()
      case ExitCommand() => doExit()
        
      case NoOpCommand() => ;
      case unknown: UnknownCommand => tell("Unknown command... Type [help] for a list of available commands: " + unknown)
    }

    if (!exitRepl) start()
  }
}
