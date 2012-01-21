package pl.project13.tinytermpm.cli.parsing.command

case class CreateCommand() extends ApiCommand

case class CreateTaskCommand() extends CreateCommand

case class CreateStoryCommand() extends CreateCommand