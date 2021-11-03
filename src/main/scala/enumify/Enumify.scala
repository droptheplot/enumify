package enumify

import java.io.{File, FileWriter}
import java.sql.Connection

import cats.effect.{IO, Resource}
import cats.effect.unsafe.implicits.global
import cats.implicits._
import doobie.util.transactor.Transactor

class Enumify(
    xa: Transactor.Aux[IO, _],
    source: Source,
    renderer: Renderer
) {
  def process: IO[List[(Enum, String)]] =
    for {
      enums <- source.enums(xa)
      renders = render(enums)
    } yield renders

  private def render(enums: List[Enum]): List[(Enum, String)] =
    enums.map { enum =>
      (enum, renderer.render(enum))
    }
}

object Enumify {
  def apply(
      connection: Connection,
      source: Source,
      renderer: Renderer
  ): File => Seq[File] = {
    val xa = Transactor.fromConnection[IO](connection)

    (directory: File) => {
      (for {
        renders <- new Enumify(xa, source, renderer).process
        files <- renders.map {
          case (enum, render) =>
            IO(directory.mkdirs()) *> writeToFile(
              new File(directory, enum.toScalaFile),
              render
            )
        }.sequence
      } yield files).unsafeRunSync
    }
  }

  private def writeToFile(file: File, render: String): IO[File] =
    Resource
      .make(IO(new FileWriter(file)))(file => IO(file.close()))
      .use(fileWriter => IO(fileWriter.write(render)))
      .as(file)
}
