package pl.project13.scala.dispatch

import dispatch.{RequestVerbs, Request}
import org.apache.http.client.methods.HttpDelete


trait DispatchFixes { this: RequestVerbs =>
  implicit def post_json(jsonBody: String): Request =
   this << (jsonBody, "text/plain")

}