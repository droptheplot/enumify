# Enumify

![CI](https://github.com/droptheplot/enumify/actions/workflows/scala.yml/badge.svg)

Enumify is a SBT plugin that will keep enums from your favorite database in sync with Scala types.

## Getting Started

Add SBT plugin to `project/plugins.sbt`

```scala
addSbtPlugin("com.github.droptheplot" % "enumify" % "0.1")
```

Add source generator to `build.sbt`

```scala
sourceGenerators in Compile += Def.task {
  val connection: java.sql.Connection = ...

  enumify.Enumify(connection, enumify.sources.PostgreSQL, enumify.renderers.Plain) {
    (sourceManaged in Compile).value / "enumify"
  }
}.taskValue
```

### Available Sources

* [PostgreSQL](https://www.postgresql.org/docs/current/datatype-enum.html)
* [MySQL](https://dev.mysql.com/doc/refman/8.0/en/enum.html)

### Available Renderers

#### Plain

```scala
sealed abstract class Mood(value: String)

object Mood {
  case object Sad extends Mood("sad")
  case object Ok extends Mood("ok")
  case object Happy extends Mood("happy")
}
```

#### Enumeratum

```scala
sealed trait Mood extends EnumEntry

object Mood extends Enum[Mood] {
  case object Sad extends Mood
  case object Ok extends Mood
  case object Happy extends Mood

  val values = findValues
}
```

### Contributing

Use `sbt "testOnly -- -l IntegrationTest"` to run only unit test.
