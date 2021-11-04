package enumify

import enumify.Enum.Value

case class Enum(schema: String, name: String, values: List[Value]) {
  def toScalaId: String = Enum.toScalaId(name)

  def toScalaFile: String = s"$toScalaId.scala"
}

object Enum {
  case class Value(value: String) extends AnyVal {
    def toScalaId: String = Enum.toScalaId(value)
  }

  def toScalaId(value: String): String =
    if (value.toList.forall(_.isUpper)) value
    else
      value
        .split(Array('_', '-'))
        .map(part => titleize(part).getOrElse(""))
        .mkString

  private def titleize(value: String): Option[String] =
    value.headOption.map(_.toUpper).map(_ +: value.tail)
}
