import sbt._
import Keys._
import sbt.Project.defaultSettings

object PhpUtils extends Build {
  val baseSettings = defaultSettings ++ Seq(
    scalaVersion := "2.11.0",
    crossScalaVersions := Seq("2.10.4", "2.11.0"),
    scalacOptions ++= Seq("-feature", "-deprecation"),
    sbtVersion := "0.13.5-RC3"
  )

  lazy val common = Project(
    id = "php-utils",
    base = file("."),
    settings = baseSettings ++ Seq(
      name := "php-utils",
      organization := "com.sandinh",
      version := "1.0.1",
      resolvers ++= Seq(
        "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"
      ),
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "2.1.6" % "test"
      )
    )
  )
}