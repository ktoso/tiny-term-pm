package pl.project13.tinytermpm.marshalling

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import java.io.ByteArrayInputStream
import java.io.StringWriter

object JAXBUtil {
  def unmarshal[T](response: String)(implicit manifest: Manifest[T]): T = {
    val unmarshaller = createUnmarshaller(manifest.erasure)

    unmarshal(response, unmarshaller)
  }

  def marshal[T](objectToMarshal: T)(implicit manifest: Manifest[T]): String = {
    try {
      val ctx = JAXBContext.newInstance(manifest.erasure)
      val marshaller = ctx.createMarshaller
      val writer = new StringWriter(500)

      marshaller.marshal(objectToMarshal, writer)
      writer.toString
    } catch {
      case e: JAXBException => throw new RuntimeException(e)
    }
  }

  private def unmarshal[T](response: String, unmarshaller: Unmarshaller)(implicit manifest: Manifest[T]): T = {
    val is = new ByteArrayInputStream(response.getBytes)

    unmarshaller.unmarshal(is).asInstanceOf[T]
  }

  private def createUnmarshaller[T](clazz: Class[T]): Unmarshaller = {
    val ctx = JAXBContext.newInstance(clazz)
    ctx.createUnmarshaller
  }
}