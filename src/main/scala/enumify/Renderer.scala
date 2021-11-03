package enumify

trait Renderer {
  def render(enum: Enum): String
}
