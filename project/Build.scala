import sbt._
import Keys._
import ProguardPlugin._

object Resolvers {
  val myResolvers = Seq(
    ScalaToolsSnapshots,
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "SpyMemcached repository" at "http://files.couchbase.com/maven2/")
}

object BuildSettings {
  import Resolvers._

  // See https://github.com/siasia/xsbt-proguard-plugin
  val proguardSettings = ProguardPlugin.proguardSettings ++ Seq(
    proguardOptions ++= Seq("-include proguard.cfg")
  )

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization  := "pl.project13",
    version       := "0.1-SNAPSHOT",
    scalaVersion  := "2.9.1",
    resolvers     := myResolvers,
    parallelExecution in IntegrationTest  := false,
    scalacOptions := Seq("-unchecked")
  ) ++ proguardSettings
}

object Dependencies {
  val apacheCommonsLogging  = "commons-logging"       %   "commons-logging"       % "1.1.1"
  val apacheCommonsNet      = "commons-net"           %   "commons-net"           % "3.0.1"

  val guava                 = "com.google.guava"      %   "guava"                 % "11.0"

  val scalatest             = "org.scalatest"         %%  "scalatest"             % "1.6.1"
  val mockito               = "org.mockito"           %   "mockito-core"          % "1.8.5"

  val testing               = Seq(scalatest % "test", mockito % "test")
}

object EpgDataManagerBuild extends Build {
  import Dependencies._
  import BuildSettings._

  lazy val root: Project = Project(
    "tiny-term-pm",
    file("."),
    settings = buildSettings ++
      Seq(libraryDependencies ++= Seq() ++ testing ) ++
      Seq(
        mainClass in (Compile, packageBin)    := Some("pl.project13.tinytermpm.Runner"),
        version := "0.1"
      )

  )

}
