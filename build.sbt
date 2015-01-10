import sbtassembly.AssemblyKeys // put this at the top of the file

// Remember, sbt needs empty lines between active settings

name := "gat"

organization := "se.gigurra"

packAutoSettings

com.github.retronym.SbtOneJar.oneJarSettings

version := scala.util.Properties.envOrElse("GAT_BUILD_VERSION", "SNAPSHOT")

isSnapshot := version.value.contains("SNAPSHOT")

crossPaths := false

retrieveManaged := true

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "se.culvertsoft" % "mgen-javalib" % "0.1"

libraryDependencies += "org.jogamp.gluegen" % "gluegen-rt-main" % "2.2.4"

libraryDependencies += "org.jogamp.jogl" % "jogl-all-main" % "2.2.4"

EclipseKeys.withSource := true

EclipseKeys.relativizeLibs := false

AssemblyKeys.assemblyMergeStrategy in assembly := {
    case "gluegen-rt.dll" => MergeStrategy.first
    case "jogl_desktop.dll" => MergeStrategy.last
    case "jogl_mobile.dll" => MergeStrategy.last
    case "newt.dll" => MergeStrategy.last
    case "nativewindow_awt.dll" => MergeStrategy.last
    case "nativewindow_win32.dll" => MergeStrategy.last
    case s: String if s.endsWith(".so") => MergeStrategy.first
    case x => 
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
}
