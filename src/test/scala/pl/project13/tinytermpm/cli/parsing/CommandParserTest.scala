package pl.project13.tinytermpm.cli.parsing

import command.{CreateStoryCommand, StoriesCommand}
import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class CommandParserTest extends FlatSpec with ShouldMatchers {

  behavior of "CommandParser"

  val parseMe1 = "userstories"
  it should "parse '"+parseMe1+"' into ApiCommand" in {
    val command = CommandParser.parse(parseMe1)

    command match {
      case StoriesCommand(None) => info("matched.")
      case other => fail("didn't match. Got: "+other)
    }
  }

  val parseMe2 = "story 390"
  it should "parse '"+parseMe2+"' into ApiCommand" in {
    val command = CommandParser.parse(parseMe2)

    command match {
      case StoriesCommand(Some(390)) => info("matched.")
      case other => fail("didn't match. Got: "+other)
    }
  }
}