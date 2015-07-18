import sbt.Keys._
import sbt._

object ScamlBuild extends Build {
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

}
