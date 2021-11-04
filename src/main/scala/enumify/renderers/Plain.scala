package enumify.renderers

import enumify._

object Plain extends Renderer {
  def render(enum: Enum): String = {
    val name = enum.toScalaId

    val values = enum.values
      .map { value =>
        s"""case object ${value.toScalaId} extends $name("${value.value}")"""
      }
      .mkString("\n  ")

    s"""package enumify.${enum.schema}
       |
       |sealed abstract class $name(value: String)
       |
       |object $name {
       |  $values
       |}
       |""".stripMargin
  }
}
