package net.ssanj.intro.pbt

import org.scalatest.{Matchers, WordSpecLike}

final class AbsTest extends Matchers with WordSpecLike {
  "Abs" should {
    "return a positive value" when {
      "given a negative value" in {
        Math.abs(-1) should be (1)
      }
    }

    "return zero" when {
      "given zero" in {
        Math.abs(0) should be (0)
      }
    }

    "return a positive value" when {
      "given positive value" in {
        Math.abs(10) should be (10)
      }
    }
  }
}