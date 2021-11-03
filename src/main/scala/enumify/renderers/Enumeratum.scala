package enumify.renderers

import enumify._

object Enumeratum extends Renderer {
  def render(enum: Enum): String = {
    val name = Enum.toScala(enum.name)

    val values = enum.values
      .map { value =>
        s"case object ${Enum.toScala(value)} extends $name"
      }
      .mkString("\n  ")

    s"""package enumify
       |
       |import enumeratum._
       |
       |sealed trait $name extends EnumEntry
       |
       |object $name extends Enum[$name] {
       |  $values
       |
       |  val values = findValues
       |}
       |""".stripMargin
  }
}
