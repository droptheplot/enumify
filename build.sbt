name := "enumify"

description := "Enum from database generator."

organization := "io.github.droptheplot"
homepage := Some(url("https://github.com/droptheplot/enumify"))

version := "0.1"

scalaVersion := "2.12.0"

sbtPlugin := true
sbtVersion := "1.3.0"

scmInfo := Some(
  ScmInfo(
    url("https://github.com/droptheplot/enumify"),
    "scm:git@github.com:droptheplot/enumify.git"
  )
)

publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true

val doobieVersion = "1.0.0-RC1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.3"

libraryDependencies += "org.tpolecat" %% "doobie-core" % doobieVersion

libraryDependencies += "org.tpolecat" %% "doobie-postgres" % doobieVersion
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.24"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
