package pl.project13.tinytermpm

import cli.{JlineCli, Cli}
import cli.util.AnsiCodes._
import util.{Constants, Preferences}

class Runner(cli: Cli) {

  import cli._

  val repl = new Repl(cli)

  def init() {
    if (Preferences.areNotDefined) {
      firstStartQuiz()
      harvestQuiz()
    }

    repl.start()
  }

  def firstStartQuiz() {
    tell("Let's get you started...")
    val url = askFor("""Server Url ("http://project13.pl/tinypm/api"):""")
    val apiKey = askFor("TinyPM ApiKey:")

    tel("Saving settings... ")
    Preferences.save(serverUrl = url, apiKey = apiKey)
    tell("Done!")
  }

  def harvestQuiz() {
    var undecided = true
    while(undecided) {
      val yn = askFor("Do you want to bind your Harvest account into TinyTermPm? [y/n]")
      
      yn match {
        case "y" =>
          undecided = false

          val url = askFor("""Harvest server url ("http://project13.harvestapp.com"):""")
          val user = askFor("Harvest username:")
          val pass = askFor("Harvest password:")

          tel("Saving harvest settings... ")
          Preferences.saveHarvestDetails(url, user, pass)
          tell("Done!")

        case "n" => undecided = false
        case _ => undecided = true
      }
    }
  }
}

object Runner {
  val cli = new JlineCli
  import cli._

  def main(args: Array[String]) {
    tell(Constants.AsciiArtAppName)
    tell(Constants.AuthorHeadline)

    tell(green("Hello, "+Preferences.UserName+". How's going?"))
    tell("Your default project is: "+b(Preferences.ProjectName))

    (new Runner(cli)).init()

    tell("Happy hakking!")
    tell("")
  }
}
