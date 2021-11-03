package enumify.renderers

import enumify._

object Plain extends Renderer {
  def render(enum: Enum): String = {
    val name = Enum.toScala(enum.name)

    val values = enum.values
      .map { value =>
        s"""case object ${Enum.toScala(value)} extends $name("$value")"""
      }
      .mkString("\n  ")

    s"""package enumify
       |
       |sealed abstract class $name(value: String)
       |
       |object $name {
       |  $values
       |}
       |""".stripMargin
  }
}
