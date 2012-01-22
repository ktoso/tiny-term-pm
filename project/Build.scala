import sbt._
import Keys._
import ProguardPlugin._

object Resolvers {
  val myResolvers = Seq(
    ScalaToolsSnapshots,
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "SpyMemcached repository" at "http://files.couchbase.com/maven2/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "JLine Project Repository</name>" at "http://jline.sourceforge.net/m2repo"
  )
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

  val scalaDispatch         = "net.databinder"       %% "dispatch-http"           % "0.8.7"
  val scalajCollection      = "org.scalaj"           %% "scalaj-collection"       % "1.2"

  val scalaToolsTime        = "org.scala-tools.time" %% "time"                    % "0.5"
  val jLine                 = "jline"                 % "jline"                   % "0.9.9"

  val akkaVersion = "1.3-RC6"
  val akkaActor             = "se.scalablesolutions.akka" % "akka-actor"         % akkaVersion
  val akkaTypedActor        = "se.scalablesolutions.akka" % "akka-typed-actor"   % akkaVersion
  val akkaTestKit           = "se.scalablesolutions.akka" % "akka-testkit"       % akkaVersion
  val akkaAll               = Seq(akkaActor, akkaTypedActor, akkaTestKit)

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
      Seq(libraryDependencies ++=
            Seq(scalaDispatch, scalajCollection, jLine, scalaToolsTime) ++
            akkaAll ++
            testing ) ++
      Seq(
        mainClass in (Compile, packageBin) := Some("pl.project13.tinytermpm.Runner"),
        version := "0.1"
      )

  )

}
