//////////////////////////////////////////
////////// Build cfg

name := "gat"

organization := "se.gigurra"

version := scala.util.Properties.envOrElse("GAT_BUILD_VERSION", "SNAPSHOT")

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "se.culvertsoft" % "mgen-javalib" % "0.1"

libraryDependencies += "org.jogamp.gluegen" % "gluegen-rt" % "2.2.4"

libraryDependencies += "org.jogamp.jogl" % "jogl-all" % "2.2.4"

EclipseKeys.withSource := true

//////////////////////////////////////////
////////// CUSTOM TASKS

lazy val getJogampNatives = taskKey[Unit]("Puts jogamp libraries in resources folder")

getJogampNatives := { "python get_jogamp_natives.py" ! }

assembly <<= assembly.dependsOn(getJogampNatives)
