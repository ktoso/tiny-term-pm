package pl.project13.tinytermpm.model

object Priority extends Enumeration {
  type Priority = Value

  val MustHave = Value("MUST_HAVE")
  val ShouldHave = Value("SHOULD_HAVE")
  val CouldHave = Value("COULD_HAVE")
  val WontHave = Value("WONT_HAVE")
}