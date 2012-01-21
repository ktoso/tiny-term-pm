package pl.project13.tinytermpm.util

import scalaj.collection.Imports._

trait ScalaJConversions {

  implicit def list2list[E](l: java.util.List[E]): scala.List[E] = l.asScala.toList
  implicit def list2list[E](l: scala.List[E]): java.util.List[E] = l.asJava
}