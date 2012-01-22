package pl.project13.tinytermpm.api

import model._
import org.joda.time.DateTime


trait UserStoriesApi {

  def forProject(projectId: Long,
                 ownerId: Option[Long] = None,
                 priority: Option[Priority] = None,
                 status: Option[Status] = None,
                 createdAtFrom: Option[DateTime] = None,
                 createdAtTo: Option[DateTime] = None): List[UserStory]


  def forCurrentIterationIn(project: Project): List[UserStory] =
    forCurrentIterationIn(project.id)

  def forCurrentIterationIn(projectId: Long): List[UserStory]


  def forIteration(iterationId: Long): List[UserStory]


  def detailsFor(userStoryId: Long): UserStory

  def update(userStory: UserStory): UserStory


  def createInCurrentIteration(story: UserStory): Long

  def create(story: UserStory): Long
}