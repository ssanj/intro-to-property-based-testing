package net.ssanj.intro.pbt

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop._

/**
 * What the properties of a Diamond?
 *
 * 1. Given any letter, the diamond will start with 'A'
 * 2. Given a valid letter, the diamond will end with 'A'
 * 3. The widest point will only have the supplied letter and spaces in between
 * 4. Any line but the first and last will only have one repeated letter and spaces
 * 5. Characters will be listed in ascending order until given letter
 * 6. The lines above the widest line will be a mirror image of the lines below it
 * 7. Each line should have two spaces more in-between chars than the line above it until widest line
 * 8. Each line should have two space on the less outside chars than the line above it
 * 9. The number of lines should be the length of the any line
 */

//TODO: Move common code out.

object DiamondProps extends Properties("Diamond") with Diamond {

  private def genCharWithoutA: Gen[UpperCharWithoutA] = Gen.choose('B', 'Z').map(UpperCharWithoutA(_))

  private def genChar: Gen[UpperChar] = Gen.alphaUpperChar.map(UpperChar(_))

  property("Given a letter, the diamond will start with 'A'") =
    forAll { ch: Char =>
      printDiamond(ch).split("\n").head.contains("A")
    }

  property("Given a letter, the diamond will end with 'A'") =
    forAll(genChar) { uc: UpperChar =>
      val ch = uc.value
      printDiamond(ch).split("\n").last.contains("A")
    }

  property("The widest point will only have the supplied letter and spaces in between") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val ch = ucwa.value
      val lines = printDiamond(ch).split("\n")
      val ((wline, _), windex) = lines.map(l => (l, l.trim.length)).zipWithIndex.maxBy(_._1._2)

      ((wline.count(_ == ch) ?= 2) :| s"expected 2 [$ch] but got [$wline]") && wline.filterNot(_ == ch).trim.isEmpty :| s"expected only spaces other than [$ch] but got [$wline]"
    }

  property("Any line but the first and last will only have one repeated letter and spaces") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val ch = ucwa.value
      val lines = printDiamond(ch).split("\n")
      val remaining = lines.tail.init //drop 'A's
      val rangeWithoutA = ('B' to 'Z')

      remaining.forall { l =>
        val unique = l.toSet
        l.filterNot(_ == ' ').length == 2 &&
        unique.size == 2 &&
        unique.contains(' ') &&
        unique.exists(rangeWithoutA.contains)
      }
    }

  property("Characters will be listed in ascending order until given letter") =
    forAll(genCharWithoutA) { uc: UpperCharWithoutA =>
      val ch = uc.value
      val lines = printDiamond(ch).split("\n")
      val uniqueChars = lines.map(_.filterNot(_ == ' ').head).slice(0, lines.length / 2)
      uniqueChars.zip(uniqueChars.tail).forall { case (c1, c2) => c1 < c2 }
    }

  property("The lines above the widest line will be a mirror image of the lines below it") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val ch = ucwa.value
      val lines = printDiamond(ch).split("\n")
      val (_, windex) = lines.map(_.trim.length).zipWithIndex.maxBy(_._1)
      val before = lines.slice(0, windex).toList
      val after  = lines.slice(windex + 1, lines.length).reverse.toList
      (before == after) :| s"actual: [${before.mkString(",")}], expected: [${after.mkString(",")}]"
    }

  property("Each line should have two spaces more in-between chars than the line above it until widest line") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val ch     = ucwa.value
      val lines  = printDiamond(ch).split("\n").map(_.trim)
      val mid    = lines.length / 2 //always odd
      val above  = lines.slice(0, mid + 1)

      val spaceWithLineIndex = above.tail.map(_.count(_ == ' ')).zipWithIndex
      spaceWithLineIndex.zip(spaceWithLineIndex.tail).forall{
        case (first, second) => second._1 - first._1 == 2
      }
    }

 property("The number of lines should be the length of the any line") =
  forAll(genChar) { uc: UpperChar =>
    val ch    = uc.value
    val lines = printDiamond(ch).split("\n")
    forAll(Gen.choose(0, lines.length - 1)) { index: Int =>
      lines(index).length ?= lines.length
    }
  }
}