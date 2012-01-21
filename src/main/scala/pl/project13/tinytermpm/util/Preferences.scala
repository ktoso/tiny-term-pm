package pl.project13.tinytermpm.util

import Constants._
import PathConversions._
import Using._
import java.util.Properties
import java.io.{FileOutputStream, FileInputStream}

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

  def saveUserDetails(id: Int, name: String = "") {
    val props = new Properties
        
    props.put("server.url", ServerUrl)
    props.put("api.key", ApiKey)
    
    props.put("user.id", id.toString)
    props.put("user.name", name)

    using(new FileOutputStream(PreferencesFile)) { fos =>
      props.store(fos, "TinyTermPM preferences - "+Version)
    }
  }
  
  def save(serverUrl: String, apiKey: String) {
    val props = new Properties
    
    props.put("server.url", serverUrl)
    props.put("api.key", apiKey)

    using(new FileOutputStream(PreferencesFile)) { fos =>
      props.store(fos, "TinyTermPM preferences - "+Version)
    }
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
}