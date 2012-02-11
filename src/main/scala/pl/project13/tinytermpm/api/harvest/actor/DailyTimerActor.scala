package pl.project13.tinytermpm.api.harvest.actor

import pl.project13.tinytermpm.api.harvest.DailyTimerApi
import akka.actor.TypedActor
import pl.project13.tinytermpm.api.HttpDispatch
import pl.project13.tinytermpm.marshalling.JAXBUtil
import pl.project13.tinytermpm.api.tinypm.model.Project
import pl.project13.tinytermpm.util.{HarvestPreferences, ScalaJConversions, ApiPreferences}
import pl.project13.tinytermpm.api.harvest.model.Daily
import pl.project13.tinytermpm.cli.parsing.time.TimeSpan
import org.joda.time.{Duration, DateTime}

class DailyTimerActor(config: HarvestPreferences) extends TypedActor with DailyTimerApi
  with HttpDispatch
  with ScalaJConversions {

  import dispatch._

  def stop() {
    println("implement me") // todo implement this
  }

  def timeToday() = {
    timeOnDay(new DateTime)
  }

  def timeDuring(startDay: DateTime, days: Int): Double = {
    val timesOnDays = for (dayNumber <- 0 until days)
      yield timeOnDay(startDay.plusDays(dayNumber))

    timesOnDays.foldLeft(0.0) { _ + _.toDouble }
  }

  def timeOnDay(day: DateTime): TimeSpan = {
    val year = day.year.get
    val dayOfYear = day.dayOfYear.get

    val urlz = config.harvestUrl(List("daily", dayOfYear, year).mkString("/"))
    
    val response = h(url(urlz) <:< AcceptXml as_!(config.HarvestUsername, config.HarvestPassword) as_str)

    val daily = JAXBUtil.unmarshal[Daily](response)

    val totalHours = daily.dayEntries.foldLeft(0.0) { _ + _.hours }
    TimeSpan(totalHours)
  }
  


}