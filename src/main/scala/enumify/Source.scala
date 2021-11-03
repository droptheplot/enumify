package enumify

import cats.effect.IO
import doobie.Transactor

trait Source {
  def enums(xa: Transactor[IO]): IO[List[Enum]]
}
