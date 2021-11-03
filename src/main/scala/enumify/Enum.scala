package enumify

import enumify.Enum._

case class Enum(name: String, values: List[String]) {
  def toScalaName: String = toScala(name)

  def toScalaFile: String = s"$toScalaName.scala"
}

object Enum {
  def toScala(value: String): String =
    if (value.toList.forall(_.isUpper)) value
    else
      value
        .split(Array('_', '-'))
        .map(part => titleize(part).getOrElse(""))
        .mkString

  private def titleize(value: String) =
    value.headOption.map(_.toUpper).map(_ +: value.tail)
}
