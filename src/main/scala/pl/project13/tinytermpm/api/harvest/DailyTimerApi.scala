package pl.project13.tinytermpm.api.harvest

import pl.project13.tinytermpm.cli.parsing.time.TimeSpan

trait DailyTimerApi {
  def timeToday(): TimeSpan

  def stop()
}
