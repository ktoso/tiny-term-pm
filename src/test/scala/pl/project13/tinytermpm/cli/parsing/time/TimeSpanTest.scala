package pl.project13.tinytermpm.cli.parsing.time

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

class TimeSpanTest extends FlatSpec with ShouldMatchers {
  
  behavior of "TimeSpan"
  
  it should "be created from a double representation" in {
    // given
    val time = 2.75
    
    // when
    val span = TimeSpan(time)
    
    // then
    span.hours should equal (2)
    span.minutes should equal (45)
  }
}
