lazy val root: Project = (project in file(".")).enablePlugins(JavaAppPackaging)

name := "internal-api"

organization := "com.revolut"

version := "0.0.1"

scalaVersion := "2.11.8"

mappings in Universal += {
  val conf: File = (resourceDirectory in Compile).value / "logback.xml"
  conf -> "conf/logback.xml"
}

mappings in Universal += {
  val conf: File = (resourceDirectory in Compile).value / "application.conf"
  conf -> "conf/application.conf"
}

mappings in Universal += {
  file("Readme.md") -> "Readme.md"
}

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.9",
  "com.typesafe" % "config" % "1.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
)