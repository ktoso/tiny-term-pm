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
import akka.dispatch.{Future, Futures}
import scala.collection.JavaConversions._


class DailyTimerActor(config: HarvestPreferences) extends TypedActor with DailyTimerApi
  with HttpDispatch {

  import dispatch._

  def stop() {
    println("implement me") // todo implement this
  }

  def timeToday() = {
    timeOnDay(new DateTime)
  }

  def timeDuring(startDay: DateTime, days: Int): TimeSpan = {
    val timesOnDaysFutures = for (dayNumber <- 0 until days)
      yield Future { timeOnDay(startDay.plusDays(dayNumber)) }

    val timesOnDays = Futures.sequence(timesOnDaysFutures.toList).get

    val totalTime = timesOnDays.foldLeft(0.0) { _ + _.toDouble }

    TimeSpan(totalTime)
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