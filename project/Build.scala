import sbt._
import Keys._
import sbt.Project.defaultSettings

object Build extends sbt.Build {
  val baseSettings = Defaults.coreDefaultSettings ++ Seq(
    scalaVersion := "2.11.0",
    crossScalaVersions := Seq("2.10.4", "2.11.0"),
    scalacOptions ++= Seq("-feature", "-deprecation")
  )

  lazy val common = Project(
    id = "php-utils",
    base = file("."),
    settings = baseSettings ++ Seq(
      name := "php-utils",
      organization := "com.sandinh",
      version := "1.0.1",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.6" % "test"
    )
  )
}