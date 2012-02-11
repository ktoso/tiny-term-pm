package pl.project13.tinytermpm.api.harvest

import pl.project13.tinytermpm.cli.parsing.time.TimeSpan
import org.joda.time.DateTime

trait DailyTimerApi {
  def timeToday(): TimeSpan
  def timeOnDay(day: DateTime): TimeSpan
  def timeDuring(startDay: DateTime, days: Int): Double

  def stop()
}
