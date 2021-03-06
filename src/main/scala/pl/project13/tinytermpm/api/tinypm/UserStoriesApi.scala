package pl.project13.tinytermpm.api.tinypm

import model._
import org.joda.time.DateTime
import pl.project13.tinytermpm.api.tinypm.model.{Project, UserStory, Status, Priority}


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


  def deleteStory(userStoryId: Long)

  def detailsFor(userStoryId: Long): Option[UserStory]

  def update(userStory: UserStory): UserStory


  def create(story: UserStory, addDefaultTasks: Boolean = false)
}