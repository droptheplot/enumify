name := "enumify"

organization := "com.github.droptheplot"

version := "0.1"

scalaVersion := "2.12.0"

sbtPlugin := true
sbtVersion := "1.3.0"

val doobieVersion = "1.0.0-RC1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.3"

libraryDependencies += "org.tpolecat" %% "doobie-core" % doobieVersion
libraryDependencies += "org.tpolecat" %% "doobie-postgres" % doobieVersion
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.24"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"

//sourceGenerators in Compile += Def.task {
//  val file = (sourceManaged in Compile).value / "demo" / "Test.scala"
//  IO.write(file,
//    """package demo
//      |
//      |object Demo { def qwe = "yo" }""".stripMargin)
//  Seq(file)
//}.taskValue
//
