package pl.project13.tinytermpm

import annotation.tailrec
import api._
import api.actor._
import cli.Cli
import cli.parsing.command._
import cli.parsing.CommandParser
import akka.actor.TypedActor
import akka.util.duration._
import akka.dispatch.{Future, Futures}
import cli.util._
import pl.project13.tinytermpm.api.model.{User, Task, UserStory}
import util.verb.Quittable._
import cli.util.ColorizedStrings._
import collection.immutable.List
import util.{ScalaJConversions, Constants, Preferences}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import java.util.Collections

class Repl(cli: Cli) {
  implicit val _cli = cli // for fluent quittable usage
  import cli._

  tell("Loading REPL...")

  var exitRepl = false

  val users = TypedActor.newInstance(classOf[UsersApi], new UsersActor(Preferences), (10 seconds).toMillis)
  val stories = TypedActor.newInstance(classOf[UserStoriesApi], new UserStoriesActor(Preferences), (10 seconds).toMillis)
  val tasks = TypedActor.newInstance(classOf[TasksApi], new TasksActor(Preferences), (10 seconds).toMillis)
  val projects = TypedActor.newInstance(classOf[ProjectsApi], new ProjectsActor(Preferences), (10 seconds).toMillis)
  val iterations = TypedActor.newInstance(classOf[IterationsApi], new IterationsActor(Preferences), (10 seconds).toMillis)

  val allActors = users :: stories :: projects :: iterations :: Nil

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
          tell("%d - %s".format(id, name))
        }
    }
  }

  def doMyCurrentTasks() {
    val userStories = stories.forCurrentIterationIn(Preferences.ProjectId)

    val futures = for (userStory <- userStories)
      yield Future {
        val detailedTasks  = tasks
          .forUserStory(userStory)
          .par
          .map { task => tasks.detailsFor(task.id).get }
        (userStory, detailedTasks)
      }

    val storiesAndTasks = Futures.sequence(futures, (60 millis).toMillis).get
    for((story, tasks) <- storiesAndTasks) {
      tell("Story: %s (Iteration: %s)", story.name.bold, story.iterationId)
      tasks.foreach { task =>
        import task._
        tell(" #%d [%s] - %s %s",
          id, Safe(status.coloredName), name,
          if(description.length > 0) "\n Desc: "+LimitedString(description, 120) else "")
      }
    }
  }

  def doTaskDetails(id: Long) {
    tasks.detailsFor(id) match {
      case Some(task) =>
        import task._
        tell("#%d [%s] - %s", task.id, status.coloredName, name)
        tell("Desc:\n%s", description)
        for(assigned <- assignedUsers) tell("Assigned: %s <%s>", assigned.name, assigned.email)
        for(comment <- comments) tell("Comment by %s on %s: %s", comment.author, PrettyDateTime(comment.date), comment.body)

      case None =>
        err("No task with id [%d] found!", id)
    }
  }

  def doHelp() {
    tell(Constants.HelpText)
  }

  def doAllUsers() {
    val allUsers = users.allUsers()
    allUsers.filter(_.active).foreach {
      u =>
        import u._
        tell("%d: %s <%s>".format(id, name, email))
    }
  }

  def doUserStories() {
    val storiesInProject = stories.forProject(Preferences.ProjectId)

    tell(""+storiesInProject.size+" stories in project ["+Preferences.ProjectName.bold+"]")
    printStories(storiesInProject)
  }
  
  def doUserStoryDetails(id: Long) {
    stories.detailsFor(id).map { story =>
      tell("""|#%d - %s
              |[tags: %s]""".stripMargin.format(story.id, story.name.bold, story.tags))
    }
  }

  def doSelfId(id: Int) {
    val user = users.detailsFor(id)
    Preferences.saveUserDetails(id, user.name)

    tell("Hello, %s!".format(user.name.bold))
  }

  def doCreateStoryInCurrentIteration() {
    tell("Create user story in "+Preferences.ProjectName.bold+"... (enter single "+"q".bold+" to abort)")

    quittable {
      val story = new UserStory

      val name = askOrQuit("Story name:")
      story.setName(name)

      val addDefaultTasks = SafeBoolean(askOrQuit("Add default tasks [y/N]"))

      tell("Fire and forget, creating user story...")
      stories.create(story, addDefaultTasks)
    }
  }
  
  def doIterations() {
    val iters = iterations.forProject(Preferences.ProjectId)

    tell("Iterations: ")
    for (iteration <- iters) {
      tell(" Iteration %d: %s", iteration.id, iteration.name.bold, iteration.startDate)
      tell("  From: %s to: %s", PrettyDate(iteration.startDate), PrettyDate(iteration.endDate))
      if (iteration.goal != "") tell("  Goal: %s", LimitedString(iteration.goal, 120))
    }
  }

  def doCreateTaskInSelectedStory() {
    tell("Select Story to create the new task for:")
    val userStories = stories.forCurrentIterationIn(Preferences.ProjectId)
    val taskStates = projects.detailsFor(Preferences.ProjectId).taskStatuses.toList
    val defaultStatus = taskStates.head

    userStories.foreach{ s => tell("#%d - %s", s.id, s.name) }

    val ids = userStories.map{ _.id }
    askForLongSelectionOrQuit("Select a user story (or "+bold("q")+"uit)", ids) match {
      case Some(storyId) =>

        val task = new Task {
          name = askFor("Task name:")
          description = askFor("Description:")
          if(askForBoolean("Assign task to you?", false)) {
            assignedUsers = Collections.singletonList(new User { id = Preferences.UserId })
          }

          taskStates.foreach { state => tell("(%s) [%s]", state.id, state.coloredName) }
          val statusId = askForLongSelection("What is the status of this task [%s]?".format(defaultStatus.name),
            taskStates.map(_.id),
            Some(defaultStatus.id))

          status = taskStates.find(_.id == statusId).get
        }

        tasks.createIn(storyId, task)
        
      case None => return
    }
  }

  def doDeleteStory(id: Long) {
    stories.detailsFor(id) match { 
      case Some(story) =>
        askForBoolean("""Delete user story: "%s"? [y/N]""".format(story.name), false) match {
          case false => return
          case _ =>
        }

        tell("Fire and forget, deleting user story...")
        stories.deleteStory(story.id)

      case None => err("No userstory with id [%d] found!", id)
    }
  }

  def doExit() {
    allActors.foreach{ TypedActor.stop(_) }
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

      case TasksCommand() => doMyCurrentTasks()
      case TaskDetailsCommand(id) => doTaskDetails(id)

      case IterationsCommand() => doIterations()
        
      case ProjectsCommand(id) => doProjects(id)

      case StoriesCommand(None) => doUserStories()
      case StoriesCommand(Some(id)) => doUserStoryDetails(id)
        
      case CreateStoryCommand() => doCreateStoryInCurrentIteration()
      case CreateCommand() => doCreateTaskInSelectedStory()

      case DeleteStoryCommand(id) => doDeleteStory(id)

      case HelpCommand() => doHelp()
      case ExitCommand() => doExit()
        
      case NoOpCommand() => ;
      case unknown: UnknownCommand => tell("Unknown command... Type [help] for a list of available commands: " + unknown)
    }

    if (!exitRepl) start()
  }
}
