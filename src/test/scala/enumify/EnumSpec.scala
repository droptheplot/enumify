package enumify

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumSpec extends AnyFunSuite with Matchers {
  test("toScala") {
    Enum.toScala("") shouldBe ""
    Enum.toScala("sad") shouldBe "Sad"

    Enum.toScala("very_sad") shouldBe "VerySad"
    Enum.toScala("very-happy") shouldBe "VeryHappy"

    Enum.toScala("_ok") shouldBe "Ok"
    Enum.toScala("ok_") shouldBe "Ok"
    Enum.toScala("ok_") shouldBe "Ok"

    Enum.toScala("OK") shouldBe "OK"
  }
}
