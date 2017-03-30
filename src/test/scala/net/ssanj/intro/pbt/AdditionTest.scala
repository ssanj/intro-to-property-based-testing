package net.ssanj.intro.pbt

import org.scalatest.{Matchers, WordSpecLike}

final class AdditionTest extends Matchers with WordSpecLike with Addition {

  // val add = scalaAdd _
  // val add = leftAdd _
  // val add = rightAdd _
  // val add = zeroAdd _
  // val add = negAdd _
  val add = hitchAdd _

  "Addition" should {
    "calculate sum" when {
      "given two numbers" in {
        add(1, 2) should be (3)
      }

      "given another two numbers" in {
        add(4, 3) should be (7)
      }
    }
  }
}
