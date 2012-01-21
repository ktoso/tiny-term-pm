package pl.project13.tinytermpm.util

import java.io.File
import PathConversions._

object Constants {

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

  val UserHomeLocation = System.getProperty("user.home")
  val UserHome = new File(UserHomeLocation)

  val PreferencesFilename = ".tiny-term-pm.config"
  val PreferencesFilePath = UserHomeLocation / PreferencesFilename
  val PreferencesFile = new File(PreferencesFilePath)
}