package pl.project13.tinytermpm.api

import akka.actor.Actor
import pl.project13.tinytermpm.util.Preferences


trait HttpDispatch {
  this: Actor =>

  val AcceptXml = HttpDispatch.AcceptXml

  import dispatch._

  val h = new Http with thread.Safety {
    override def make_logger =
      if(Preferences.LoggingOn)
        super.make_logger
      else
        new Logger {
          def info(msg: String, items: Any*) {}

          def warn(msg: String, items: Any*) {}
        }
  }

  override def postStop() {
    h.shutdown()
  }
}
object HttpDispatch {
  val AcceptXml: Map[String, String] = Map(("Accept", "application/xml"))
}