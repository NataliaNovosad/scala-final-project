import sbt.Keys.libraryDependencies

name := "scala-final-project"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.1"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.0"
// https://mvnrepository.com/artifact/net.liftweb/lift-json
libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime

lazy val root = (project in file("."))
  .settings(name := "scala-final-project")
  .aggregate(reddit)
  .aggregate(twitter_streaming)

lazy val reddit = (project in file("reddit"))
  .settings(
    name := "reddit",
    libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
    mainClass := Some("reddit-streaming/src/scala/users/SimulateUsers.scala")
  )

lazy val twitter_streaming = (project in file("twitter_streaming"))
  .settings(
    name := "twitter_streaming",
    libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
    libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.0",
    // https://mvnrepository.com/artifact/net.liftweb/lift-json
    libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.1"
  )