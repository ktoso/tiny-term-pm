package pl.project13.tinytermpm.model

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FlatSpec, FunSuite}

class PriorityTest extends FlatSpec with ShouldMatchers {

  val names =
    ("MUST_HAVE", Priority.MustHave) ::
    ("SHOULD_HAVE", Priority.ShouldHave) ::
    ("COULD_HAVE", Priority.CouldHave) ::
    ("WONT_HAVE", Priority.WontHave) ::
    Nil

  behavior of "Priority"

  for ((name, enumVal) <- names)
    it should "be parsed from [" + name + "] name" in {
      val priority = Priority.withName(name)

      priority should equal (enumVal)
    }
}