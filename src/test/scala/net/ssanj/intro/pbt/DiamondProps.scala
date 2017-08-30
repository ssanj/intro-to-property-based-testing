package net.ssanj.intro.pbt

import org.scalacheck.{Gen, Properties, Shrink}
import org.scalacheck.Prop._

/**
 * What the properties of a Diamond?
 *
 * 1. Given a letter, the diamond will start with 'A'
 * 2. Given a letter, the diamond will end with 'A'
 * 3. The widest point will only have the supplied letter and spaces
 * 4. Characters will be listed in ascending order until given letter and then descend
 * 5. The lines above the widest line will be a mirror image of the lines below it
 * 6. Each line should have two spaces more in-between chars than the line above it until widest line
 * 7. Each line should have one space less outside chars than the line above it
 * 8. All unhandled inputs should return 'A'
 * 9. The number of lines should be (if A => 1, else => (2 * diff) + 1)
 * 10. Always an odd number of lines
 */

object DiamondProps extends Properties("Diamond") with Diamond {

  private final case class DiamondChar(value: Char)
  private final case class DiamondCharWithoutA(value: Char)

  private def genCharWithoutA: Gen[DiamondChar] = Gen.choose('B', 'Z').map(DiamondChar)

  private def genChar: Gen[DiamondChar] = Gen.alphaUpperChar.map(DiamondChar)

  private implicit def shrinkDiamondChar: Shrink[DiamondChar] =
    Shrink {
      case DiamondChar(ch) => ('A' until ch).reverse.map(DiamondChar).toStream
    }

  private def shrinkDiamondCharWithoutA: Shrink[DiamondChar] =
    Shrink {
      case DiamondChar('A') => Stream.Empty
      case DiamondChar(ch) => ('B' until ch).reverse.map(DiamondChar).toStream
    }

  //How do we handle Shrinking special cases?

  property("Given a letter, the diamond will start with 'A'") =
    forAll(genChar) { dc: DiamondChar =>
      val ch = dc.value
      printDiamond(ch).split("\n").head.contains("A")
    }

  property("Given a letter, the diamond will end with 'A'") =
    forAll(genChar) { dc: DiamondChar =>
      val ch = dc.value
      printDiamond(ch).split("\n").last.contains("A")
    }

  property("The widest point will only have the supplied letter and spaces") =
    forAll(genCharWithoutA) { dc: DiamondChar =>
      val ch = dc.value
      val lines = printDiamond(ch).split("\n")
      val ((wline, _), windex) = lines.map(l => (l, l.trim.length)).zipWithIndex.maxBy(_._1._2)

      ((wline.count(_ == ch) ?= 2) :| s"expected 2 [$ch] but got [$wline]") && wline.filterNot(_ == ch).trim.isEmpty :| s"expected only spaces other than [$ch] but got [$wline]"
    }

  property("The lines above the widest line will be a mirror image of the lines below it") =
    forAll(genCharWithoutA) { dc: DiamondChar =>
      val ch = dc.value
      val lines = printDiamond(ch).split("\n")
      val (_, windex) = lines.map(_.trim.length).zipWithIndex.maxBy(_._1)
      val before = lines.slice(0, windex).toList
      val after  = lines.slice(windex + 1, lines.length).reverse.toList
      (before == after) :| s"actual: [${before.mkString(",")}], expected: [${after.mkString(",")}]"
    }

  property("Each line should have two spaces more in-between chars than the line above it until widest line") =
    forAll(genCharWithoutA) { dc: DiamondChar =>
      val ch     = dc.value
      val lines  = printDiamond(ch).split("\n").map(_.trim)
      val mid    = lines.length / 2 //always odd
      val above  = lines.slice(0, mid + 1)

      val spaceWithLineIndex = above.tail.map(_.count(_ == ' ')).zipWithIndex
      spaceWithLineIndex.zip(spaceWithLineIndex.tail).forall{
        case (first, second) => second._1 - first._1 == 2
      }
    }(implicitly, shrinkDiamondCharWithoutA, implicitly)
}