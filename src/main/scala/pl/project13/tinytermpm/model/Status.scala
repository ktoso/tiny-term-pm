package pl.project13.tinytermpm.model

object Status extends Enumeration {
  type Status = Value

  val Pending = Value("PENDING")
  val InProgress = Value("IN_PROGRESS")
  val Completed = Value("COMPLETED")
}
