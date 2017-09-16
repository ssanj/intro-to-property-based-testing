package net.ssanj.intro.pbt

import org.scalatest.{Matchers, WordSpecLike}

final class DiamondTest extends Matchers with WordSpecLike with DiamondBroken {
  "Diamond Kata" should {
    "return an 'A'" when {
      "given an 'A'" in {
        printDiamond('A') should be ("A")
      }
    }

    "return a diamond for 'B'" when {
      "given a 'B'" in {
        val diamondB =
          """|-A-
             |B*B
             |-A-""".stripMargin

        printDiamond('B') should be (diamondB)
      }
    }

    "return a diamond for 'C'" when {
      "given a 'C'" in {
        val diamondC =
          """|--A--
             |-B*B-
             |C***C
             |-B*B-
             |--A--""".stripMargin

        printDiamond('C') should be (diamondC)
      }
    }
  }
}