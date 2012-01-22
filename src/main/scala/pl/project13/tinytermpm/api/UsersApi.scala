package pl.project13.tinytermpm.api

import model.User

trait UsersApi {
  def allUsers(): List[User]
  def detailsFor(id: Int): User
}
