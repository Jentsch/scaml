name := "scaml"

version := "0.4.0-SNAPSHOT"

organization := "de.jentsch"

description := "A Scala innerDSL to describe documents"

licenses += ("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

scalaVersion := "3.3.1"

// Compiler options
scalacOptions ++=
  Seq("-sourcepath", baseDirectory.value.getAbsolutePath)

scalacOptions ++= Seq("-deprecation", "-feature")

// Tests
libraryDependencies += "org.specs2" %% "specs2-core" % "5.5.1" % Test

// API
Compile / doc / scalacOptions ++= Opts.doc.sourceUrl("https://github.com/Jentsch/scaml/blob/master€{FILE_PATH}.scala")

Compile / doc / scalacOptions ++= Opts.doc.title("Scaml")

val web = TaskKey[File]("web", "Creates api doc and tests'")

val target = new File("target/web/")
val webSource = new File("web/")

val webTask =
  web := {
    val log = streams.value.log

    val docs = (doc in Compile).value

    IO.copyDirectory(webSource, target)
    IO.copyDirectory(docs, target / "api")

    log.success("Generated web page placed at " + target)

    target
  }

lazy val root = Project(id = "main", base = file(".")).settings(webTask)
