package pl.project13.tinytermpm.util

import Constants._
import PathConversions._
import verb.Using._
import java.util.Properties
import java.io.{FileNotFoundException, FileOutputStream, FileInputStream}
import com.github.tototoshi.base64.Base64
import pl.project13.tinytermpm.api.tinypm.model.Project

trait ApiPreferences {
  def ServerUrl: String
  def ApiKey: String

  // todo replace with my URL DSL
  def apiUrl(path: String) = {
    val tokenPart = if(path.contains("?")) "&token=" + ApiKey else "?token=" + ApiKey
    ServerUrl/path+tokenPart
  }
}

trait HarvestPreferences {
  def HarvestServerUrl: String
  def HarvestUsername: String
  def HarvestPassword: String
  
  // todo replace with my URL DSL
  def harvestUrl(path: String) = {
    HarvestServerUrl/path
  }
}

trait Preferences
  extends ApiPreferences
  with HarvestPreferences

object Preferences extends Preferences {
  val Version = "v0.1"

  def areDefined = PreferencesFile.exists()
  def areNotDefined = !areDefined

  def saveHarvestDetails(url: String, username: String, password: String) {
    val props = loadProps

    props.put("harvest.server.url", url)
    props.put("harvest.username", username)
    props.put("harvest.password", Base64.encode(password.getBytes))

    saveProps(props)
  }
  
  def saveUserDetails(id: Int, name: String = "") {
    val props = loadProps
    
    props.put("user.id", id.toString)
    props.put("user.name", name)

    saveProps(props)
  }

  def saveProject(project: Project) {
    val props = loadProps

    props.put("project.id", project.id.toString)
    props.put("project.name", project.name)

    saveProps(props)
  }

  def save(serverUrl: String, apiKey: String) {
    val props = loadProps
    
    props.put("server.url", serverUrl)
    props.put("api.key", Base64.encode(apiKey.getBytes))

    saveProps(props)
  }

  private def saveProps(props: Properties) {
    using(new FileOutputStream(PreferencesFile)) { fos =>
      props.store(fos, "TinyTermPM preferences - "+Version)
    }
  }
  
  def loadProps = {
    val properties = new Properties

    try { using (new FileInputStream(PreferencesFile)) {
      properties.load(_)
    }} catch {
      case _ : FileNotFoundException => //System.err.println("No existing tiny-term-pm config file found...")
    }

    properties
  }

  lazy val ServerUrl = loadProps.getProperty("server.url")
  lazy val ApiKey = new String(Base64.decode(loadProps.getProperty("api.key")).map(_.toChar).toArray)

  lazy val HarvestServerUrl = loadProps.getProperty("harvest.server.url")
  lazy val HarvestUsername = loadProps.getProperty("harvest.username")
  lazy val HarvestPassword = new String(Base64.decode(loadProps.getProperty("harvest.password")).map(_.toChar).toArray)

  // look like a constant ;-)
  def UserName = loadProps.getProperty("user.name", "Anonymous")
  def UserId = loadProps.getProperty("user.id").toInt

  def ProjectId = loadProps.getProperty("project.id", "0").toLong
  def ProjectName = loadProps.getProperty("project.name", "")
}
