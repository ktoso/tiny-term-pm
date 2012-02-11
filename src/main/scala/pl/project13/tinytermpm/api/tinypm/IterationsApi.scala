package pl.project13.tinytermpm.api.tinypm

import model.{Iteration, Project}

trait IterationsApi {
  def forProject(project: Project): List[Iteration] = forProject(project.id)

  def forProject(projectId: Long): List[Iteration]

  def currentForProject(projectId: Long): Option[Iteration]
}