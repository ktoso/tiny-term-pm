package pl.project13.tinytermpm.util.verb

object Unless {

  def unless(test: Boolean)(block: => Unit) {
    if(!test)
      block
  }

  def unless(test: () => Boolean)(block: => Unit) {
    if(!test())
      block
  }
}