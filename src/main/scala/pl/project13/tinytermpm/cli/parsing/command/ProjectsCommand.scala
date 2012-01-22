package pl.project13.tinytermpm.cli.parsing.command

case class ProjectsCommand(projectId: Option[Long] = None) extends ApiCommand
