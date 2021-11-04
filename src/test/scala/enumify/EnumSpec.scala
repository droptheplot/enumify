package enumify

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumSpec extends AnyFunSuite with Matchers {
  test("toScalaId") {
    Enum.toScalaId("") shouldBe ""
    Enum.toScalaId("sad") shouldBe "Sad"

    Enum.toScalaId("very_sad") shouldBe "VerySad"
    Enum.toScalaId("very-happy") shouldBe "VeryHappy"

    Enum.toScalaId("_ok") shouldBe "Ok"
    Enum.toScalaId("ok_") shouldBe "Ok"
    Enum.toScalaId("ok_") shouldBe "Ok"

    Enum.toScalaId("OK") shouldBe "OK"
  }
}
