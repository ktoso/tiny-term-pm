package pl.project13.tinytermpm.api.tinypm

import model.User


trait UsersApi {
  def allUsers(): List[User]

  def detailsFor(id: Int): User
}
