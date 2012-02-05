package pl.project13.tinytermpm.cli.util

object Safe {
  def apply[T: Manifest](op: => T): T = {
    try {
      op
    } catch {
      case e: Throwable =>
        if(implicitly[Manifest[T]].erasure.isAssignableFrom(classOf[String])) 
          "".asInstanceOf[T]
        else if(implicitly[Manifest[T]].erasure.isAssignableFrom(classOf[Int]))
          0.asInstanceOf[T]
        else if(implicitly[Manifest[T]].erasure.isAssignableFrom(classOf[Long]))
          0.asInstanceOf[T]
        else if(implicitly[Manifest[T]].erasure.isAssignableFrom(classOf[Long]))
          (0).asInstanceOf[T]
        else 
          throw e
    }
  }
}