name := "scala-final-project"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.1"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime

lazy val root = (project in file("."))
  .settings(name := "scala-final-project")
  .aggregate(reddit_streaming)

lazy val reddit_streaming = (project in file("reddit-streaming"))
  .settings(
    name := "reddit-streaming",
    libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
  )
