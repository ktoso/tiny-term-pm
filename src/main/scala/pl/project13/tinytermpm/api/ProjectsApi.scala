package pl.project13.tinytermpm.api

import model.Project

trait ProjectsApi {
  def all(): List[Project]
  def detailsFor(id: Long): Project
}