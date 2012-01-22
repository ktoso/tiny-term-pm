package pl.project13.tinytermpm.api

import model.Activity

trait TimeSheetApi {

  def forCurrentIteration(userId: Option[Long] = None): List[Activity]

  def forIteration(iterationId: Long,
                   userId: Option[Long] = None): List[Activity]

  def forTask(taskId: Long,
              userId: Option[Long] = None): List[Activity]

  def create(taskId: Long, activity: Activity)
}