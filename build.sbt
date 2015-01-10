
// Remember, sbt needs empty lines between active settings

name := "gat"

organization := "se.gigurra"

packAutoSettings

com.github.retronym.SbtOneJar.oneJarSettings

version := scala.util.Properties.envOrElse("GAT_BUILD_VERSION", "SNAPSHOT")

isSnapshot := version.value.contains("SNAPSHOT")

crossPaths := false

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "se.culvertsoft" % "mgen-javalib" % version.value

libraryDependencies += "org.jogamp.gluegen" % "gluegen-rt-main" % "2.2.1"

libraryDependencies += "org.jogamp.jogl" % "jogl-all-main" % "2.2.4"

EclipseKeys.withSource := true

EclipseKeys.relativizeLibs := false
