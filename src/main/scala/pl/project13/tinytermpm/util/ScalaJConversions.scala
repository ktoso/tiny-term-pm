package pl.project13.tinytermpm.util

import scalaj.collection.Imports._
import java.util.Collections

trait ScalaJConversions {

  implicit def list2list[E](l: java.util.List[E]): scala.List[E] = if(l == null) Nil else l.asScala.toList
  implicit def list2list[E](l: scala.List[E]): java.util.List[E] = if(l == null) Collections.emptyList() else l.asJava
}