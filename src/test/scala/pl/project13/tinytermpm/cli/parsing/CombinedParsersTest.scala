package pl.project13.tinytermpm.cli.parsing

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import util.parsing.combinator.JavaTokenParsers


class CombinedParsersTest extends FlatSpec with ShouldMatchers {

  val parser = new TestParser
  
  behavior of "CombinedParser"

  it should "combine [create task] commands" in {
    val first = List("create" , "c")
    val second = "task" :: Nil

    val validPairs = "c task" :: "create task" :: Nil
    validPairs.foreach{ valid =>

      // when
      val combined = parser.combinedParser(first: _*)(second: _*)

      // then
      parser.parse(combined, valid) should equal (valid)
      info("[" + valid + "] was parsed ok")
    }
  }
}

class TestParser extends JavaTokenParsers with CombinedParsers {
  
  def parse(parser: Parser[String], content: String): String = {
    parseAll(parser, content.trim) match {
      case Success(res: String, _) => res
      case x => throw new RuntimeException(x.toString)
    }
  }
}