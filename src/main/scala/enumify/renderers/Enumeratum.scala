package enumify.renderers

import enumify._

object Enumeratum extends Renderer {
  def render(enum: Enum): String = {
    val name = enum.toScalaId

    val values = enum.values
      .map { value =>
        s"""case object ${value.toScalaId} extends $name("${value.value}")"""
      }
      .mkString("\n  ")

    s"""package enumify.${enum.schema}
       |
       |import enumeratum._
       |
       |sealed abstract class $name(override val entryName: String) extends EnumEntry
       |
       |object $name extends Enum[$name] {
       |  $values
       |
       |  val values = findValues
       |}
       |""".stripMargin
  }
}
