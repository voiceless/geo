name := "geo"

version := "1.0"

scalaVersion := "2.11.8"

mainClass in(Compile, run) := Some("Boot")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.0.0",
  "org.scaldi" %% "scaldi" % "0.5.7",
  "org.scaldi" %% "scaldi-akka" % "0.5.7",
  "org.scalatest" %% "scalatest" % "3.0.0",
  "io.spray" %% "spray-can" % "1.3.1",
  "io.spray" %% "spray-routing" % "1.3.1",
  "io.spray" %% "spray-json" % "1.3.2",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.10",
  "com.typesafe" % "config" % "1.3.0"
)