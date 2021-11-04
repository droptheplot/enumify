package enumify.sources

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import enumify.Enum

class MySQLSpec extends AnyFunSuite with Matchers with BeforeAndAfterAll {
  lazy val xa: Transactor.Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "com.mysql.jdbc.Driver",
    "jdbc:mysql://localhost:3306/database",
    "mysql",
    "mysql"
  )

  override def beforeAll(): Unit = {
    sql"""
      create table notes (
        mood enum('sad', 'ok', 'happy')
      );
      """.update.run
      .transact(xa)
      .unsafeRunSync
  }

  test("Fetch MySQL enums", IntegrationTest) {
    MySQL
      .enums(xa)
      .map { enums =>
        enums.map { enum =>
          enum.schema shouldBe "database"
          enum.name shouldBe "mood"
          enum.values should contain theSameElementsAs List(
            Enum.Value("sad"),
            Enum.Value("ok"),
            Enum.Value("happy")
          )
        }
      }
      .unsafeRunSync
  }
}
