package pl.project13.tinytermpm.api

import model.{UserStory, Task}


trait TasksApi {

  def forUserStory(story: UserStory): List[Task] =
    forUserStory(story.id)

  def forUserStory(storyId: Long): List[Task]


  def createIn(story: UserStory, task: Task): Long


  def detailsFor(taskId: Long): Task
  
  
  def update(task: Task)
  
  
  def delete(task: Task) {
    delete(task.id)
  }

  def delete(taskId: Long): Unit
}