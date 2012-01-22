package pl.project13.tinytermpm.util

import Constants._
import PathConversions._
import Using._
import java.io.{FileOutputStream, FileInputStream}
import pl.project13.tinytermpm.api.model.Project
import java.util.Properties

trait ApiPreferences {
  def ServerUrl: String
  def ApiKey: String

  def apiUrl(path: String) = ServerUrl/path+"?token="+ApiKey
}

trait UserPreferences

trait Preferences
  extends UserPreferences
  with ApiPreferences

object Preferences extends Preferences {
  val Version = "v0.1"

  def areDefined = PreferencesFile.exists()
  def areNotDefined = !areDefined

  private def saveProps(props: Properties) {
    using(new FileOutputStream(PreferencesFile)) { fos =>
      props.store(fos, "TinyTermPM preferences - "+Version)
    }
  }

  def saveUserDetails(id: Int, name: String = "") {
    val props = new Properties
        
    props.put("server.url", ServerUrl)
    props.put("api.key", ApiKey)
    
    props.put("user.id", id.toString)
    props.put("user.name", name)

    saveProps(props)
  }
  
  def saveProject(project: Project) {
    val props = new Properties

    props.put("server.url", ServerUrl)
    props.put("api.key", ApiKey)

    props.put("user.id", UserId.toString)
    props.put("user.name", UserName)

    props.put("project.id", project.id.toString)
    props.put("project.name", project.name)

    saveProps(props)
  }
  
  def save(serverUrl: String, apiKey: String) {
    val props = new Properties
    
    props.put("server.url", serverUrl)
    props.put("api.key", apiKey)

    saveProps(props)
  }
  
  def props = {
    val properties = new Properties
    using (new FileInputStream(PreferencesFile)) { fis =>
      properties.load(fis)
    }
    properties
  }

  lazy val ServerUrl = props.getProperty("server.url")
  lazy val ApiKey = props.getProperty("api.key")
  
  // look like a constant ;-)
  def UserName = props.getProperty("user.name", "Anonymous") 
  def UserId = props.getProperty("user.id").toInt

  def ProjectId = props.getProperty("project.id", "")
  def ProjectName = props.getProperty("project.name", "")
}