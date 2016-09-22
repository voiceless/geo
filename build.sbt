name := "geo"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.0.0",
  "org.scaldi" %% "scaldi" % "0.5.7",
  "org.scalatest" %% "scalatest" % "3.0.0"
)