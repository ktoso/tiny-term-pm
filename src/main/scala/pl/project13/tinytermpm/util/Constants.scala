package pl.project13.tinytermpm.util

import java.io.File
import PathConversions._
import pl.project13.tinytermpm.cli.util.AnsiCodes._

object Constants {

  private implicit def tuple2mkStringable(t: Tuple2[String, String]) = new {
    def mkString =  t._1 + " " + t._2
  }

  val AsciiArtAppName = """
    _______ _          _______                  _____  __  __
   |__   __(_)        |__   __|                |  __ \|  \/  |
      | |   _ _ __  _   _| | ___ _ __ _ __ ___ | |__) | \  / |
      | |  | | '_ \| | | | |/ _ \ '__| '_ ` _ \|  ___/| |\/| |
      | |  | | | | | |_| | |  __/ |  | | | | | | |    | |  | |
      |_|  |_|_| |_|\__, |_|\___|_|  |_| |_| |_|_|    |_|  |_|
                     __/ |
                    |___/
"""
  val AuthorHeadline = """
   Konrad Malawski <konrad.malawski@java.pl>    project13.pl
   https://github.com/ktoso/tiny-term-pm        License: GPLv3
"""

  val HelpText = List(
    (b("users"), "- lists all available users").mkString,
    (b("self #"), "- sets your user id, used to make sure you only set time for yourself in").mkString,
    (b("i am #"), "  the timesheet actions").mkString,
    (b("tasks"), "- lists all available tasks").mkString,
    (b("help"), "- displays this help message").mkString,
    (b("exit / quit / q"), "- quits ttpm").mkString
  ).mkString("\n")

  val UserHomeLocation = System.getProperty("user.home")
  val UserHome = new File(UserHomeLocation)

  val PreferencesFilename = ".tiny-term-pm.config"
  val PreferencesFilePath = UserHomeLocation / PreferencesFilename
  val PreferencesFile = new File(PreferencesFilePath)
}