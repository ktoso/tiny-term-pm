package pl.project13.tinytermpm.cli.parsing.command

import org.joda.time.DateTime

sealed abstract class ApiCommand

case class NoOpCommand() extends ApiCommand

case class ProjectsCommand(projectId: Option[Long] = None) extends ApiCommand

case class TasksCommand() extends ApiCommand
case class TaskDetailsCommand(taskId: Long) extends ApiCommand

case class UnknownCommand(input: String) extends ApiCommand

case class IterationsCommand() extends ApiCommand

case class SetSelfIdCommand(id: Int) extends ApiCommand

case class UsersCommand() extends ApiCommand

case class StoriesCommand(id: Option[Long] = None) extends ApiCommand

case class CreateCommand() extends ApiCommand
case class CreateTaskCommand() extends CreateCommand
case class CreateStoryCommand() extends CreateCommand

case class DeleteCommand() extends ApiCommand
case class DeleteStoryCommand(id: Long) extends DeleteCommand
case class DeleteTasksCommand(ids: List[Long]) extends DeleteCommand

case class TimeTodayHarvestCommand() extends ApiCommand
case class TimeOnDayHarvestCommand(day: DateTime) extends ApiCommand

case class ExitCommand() extends ApiCommand

case class HelpCommand() extends ApiCommand
