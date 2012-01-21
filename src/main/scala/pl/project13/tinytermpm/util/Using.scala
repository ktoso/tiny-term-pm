package pl.project13.tinytermpm.util

object Using {
  def using[T <: { def close() }](closable: T)(block: (T) => Unit) {
    block(closable)
    closable.close()
  }
}