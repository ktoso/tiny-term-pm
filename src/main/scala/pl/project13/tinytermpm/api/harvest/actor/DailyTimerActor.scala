package pl.project13.tinytermpm.api.harvest.actor

import pl.project13.tinytermpm.api.harvest.DailyTimerApi
import akka.actor.TypedActor
import pl.project13.tinytermpm.api.HttpDispatch
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.api.tinypm.model.Project
import pl.project13.tinytermpm.util.{HarvestPreferences, ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.harvest.model.Daily
import pl.project13.tinytermpm.cli.parsing.time.TimeSpan

class DailyTimerActor(config: HarvestPreferences) extends TypedActor with DailyTimerApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def stop() {}

  def timeToday() = {
    val urlz = config.harvestUrl("daily")

    val response = h(url(urlz) <:< AcceptXml as_!(config.HarvestUsername, config.HarvestPassword) as_str)

    println("response = " + response)
    
    val daily = JAXBUtil.unmarshal[Daily](response)

    val totalHours = daily.dayEntries.foldLeft(0.0) { _ + _.hours }
    TimeSpan(totalHours)
  }

}