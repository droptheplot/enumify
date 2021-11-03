package enumify.sources

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

class PostgreSQLSpec extends AnyFunSuite with Matchers with BeforeAndAfterAll {
  lazy val xa: Transactor.Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/postgres",
    "postgres",
    "postgres"
  )

  override def beforeAll(): Unit = {
    sql"""
      create type mood as enum ('sad', 'ok', 'happy');
      """.update.run
      .transact(xa)
      .unsafeRunSync
  }

  test("Fetch PostgreSQL enums", IntegrationTest) {
    PostgreSQL
      .enums(xa)
      .map { enums =>
        enums.map { enum =>
          enum.name shouldBe "mood"
          enum.values should contain theSameElementsAs List(
            "sad",
            "ok",
            "happy"
          )
        }
      }
      .unsafeRunSync
  }
}
