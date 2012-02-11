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
      if (buffer.isEmpty || partialMatch(buffer, command))
        candidates.add(command)
    }

    List(
      "users", "user",
      "create task", "c task",
      "stories",
      "create story ", "create userstory ", "c story ", "c userstory ",
      "delete story ", "delete userstory ", "d story ", "d userstory ",
      "delete task ", "d task ",
      "projects", "project",
      "iterations",
      "time today", "t today", "tt",
      "time yesterday", "t yesterday",
      "time week", "t week",
      "time month", "t month",
      "i am", "self",
      "userstories",
      "tasks",
      "comments",
      "timesheet",
      "whoami",
      "help",
      "quit", "exit"
    ).foreach(addIfMatchOrEmptyBuffer(_))

    Collections.sort(candidates)

    0
  }

}
