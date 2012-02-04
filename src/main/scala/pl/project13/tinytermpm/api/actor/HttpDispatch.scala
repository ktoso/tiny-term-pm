package pl.project13.tinytermpm.api.actor

import akka.actor.Actor


trait HttpDispatch { this: Actor =>

  import dispatch._
  val h = new Http

  override def postStop() {
    h.shutdown()
  }
}