import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "play-session-cache"
  val appVersion = "1.2.0"

  val appDependencies = Seq(
    // Add your project dependencies here,
    cache
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "com.github.krzysztofkowalski"
  )

}
