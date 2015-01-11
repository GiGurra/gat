//////////////////////////////////////////
////////// Build cfg

name := "gat"

organization := "se.gigurra"

scalaVersion := "2.11.4"

version := scala.util.Properties.envOrElse("GAT_BUILD_VERSION", "SNAPSHOT")

crossPaths := false

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "se.culvertsoft" % "mgen-javalib" % "0.1",
  "org.jogamp.gluegen" % "gluegen-rt" % "2.2.4",
  "org.jogamp.jogl" % "jogl-all" % "2.2.4",
  "org.jogamp.gluegen" % "gluegen-rt-main" % "2.2.4" % "provided",
  "org.jogamp.jogl" % "jogl-all-main" % "2.2.4" % "provided"
)

EclipseKeys.withSource := true

//////////////////////////////////////////
////////// CUSTOM TASKS

lazy val getJogampNatives = taskKey[Unit]("Puts jogamp libraries in resources folder")

getJogampNatives := { "python get_jogamp_natives.py" ! }

getJogampNatives <<= getJogampNatives.dependsOn(compile in Compile)

assembly <<= assembly.dependsOn(getJogampNatives)
