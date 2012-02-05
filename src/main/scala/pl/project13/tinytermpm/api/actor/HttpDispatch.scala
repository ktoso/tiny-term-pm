package pl.project13.tinytermpm.api.actor

import akka.actor.Actor


trait HttpDispatch {
  this: Actor =>

  import dispatch._

  val h = new Http {
    override def make_logger =
      new Logger {
        def info(msg: String, items: Any*) { }

        def warn(msg: String, items: Any*) { }
      }
  }

override def postStop () {
h.shutdown ()
}
}