package pl.project13.tinytermpm.cli.completion

import jline.Completor
import java.lang.String
import annotation.tailrec
import java.util.{List => JList, Collections}

class CommandsCompletor extends Completor {

  @tailrec
  private def partialMatch(part: String, whole: String): Boolean =
    if (whole.length() == 0) {
      false
    } else if (part equals (whole)) {
      true
    } else {
      partialMatch(part, whole.substring(0, whole.length() - 1))
    }

  def complete(buffer: String, cursor: Int, candidatesList: JList[_]) = {
    val candidates = candidatesList.asInstanceOf[JList[String]]

    def addIfMatchOrEmptyBuffer(command: String) {
      if (buffer.isEmpty || partialMatch (buffer, command))
        candidates.add(command)
    }

    List(
      "users", "user",
      "projects", "project",
      "iterations",
      "i am", "self",
      "userstories",
      "tasks",
      "comments",
      "timesheet",
      "help",
      "quit", "exit"
    ).foreach(addIfMatchOrEmptyBuffer(_))

    Collections.sort(candidates)

    0
  }

}