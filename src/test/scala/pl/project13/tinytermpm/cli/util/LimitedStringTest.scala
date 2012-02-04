package pl.project13.tinytermpm.cli.util

import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers


class LimitedStringTest extends FlatSpec with ShouldMatchers {

  behavior of "LimitedString"

  it should "trim too long string" in {
    LimitedString("aaaaaaaaaaaa", 6, "").toString should equal ("aaaaaa")
  }

  it should "trim with default limit sign" in {
    LimitedString("aaaaaaaaaaaa", 6).toString should equal ("a[...]")
  }

  it should "not trim a not-too-long string" in {
    LimitedString("aaaa", 6).toString should equal ("aaaa")
  }

  it should "not trim by default" in {
    LimitedString("aaaa").toString should equal ("aaaa")
  }
}
