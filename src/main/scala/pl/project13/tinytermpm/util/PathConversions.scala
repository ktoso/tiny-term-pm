package pl.project13.tinytermpm.util

import java.io.File

object PathConversions {
  implicit def str2slashablePath(str: String) = SlashablePath(str)
}

case class SlashablePath(left: String) {
  private val PathSeparator = File.separator

  def /(right: Any) = Seq(left, right.toString).mkString(PathSeparator)
}