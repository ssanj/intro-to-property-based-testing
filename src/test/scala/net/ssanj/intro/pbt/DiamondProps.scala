package net.ssanj.intro.pbt

import org.scalacheck.{Gen, Prop, Properties}
import org.scalacheck.Prop._

/**
 * What the properties of a Diamond?
 *
 * 1.  Given any character, the diamond will start with 'A'
 * 2.  Given any uppercase character, the diamond will end with 'A'
 * 3.  The widest point will only have the supplied letter and inChar in between
 * 4.  Any line but the first and last will only have one repeated letter and inChar and possible outChar
 * 5.  Characters will be listed in ascending order until given letter
 * 6.  The lines above the widest line will be a mirror image of the lines below it
 * 7.  Each line should have two inChar more than the line above it until widest line
 * 8.  Each line should have two outChar less than the line above it until widest line
 * 9.  Each line should be a mirror image of half itself
 * 10. The number of lines should be the length of the any line
 */

//TODO: Move common code out.

object DiamondProps extends Properties("Diamond") with DiamondBroken {

  private def genCharWithoutA: Gen[UpperCharWithoutA] = Gen.choose('B', 'Z').map(UpperCharWithoutA(_))

  private def genChar: Gen[UpperChar] = Gen.alphaUpperChar.map(UpperChar(_))

  private def upCharDiamondLines(uc: UpperChar): Seq[String] = diamondLines(uc.value)

  private def upCharWithoutADiamondLines(ucwa: UpperCharWithoutA): Seq[String] = diamondLines(ucwa.value)

  private def diamondLines(ch: Char): Seq[String] = printDiamond(ch).split("\n")

  private def all(propSeq: Seq[Prop]): Prop = Prop.all(propSeq:_*)

  private def removeInAndOut(line: String): String = line.filterNot(c => c == inChar || c == outChar)

  property("Given any character, the diamond will start with 'A'") =
    forAll { ch: Char =>
      printDiamond(ch).split("\n").head.contains("A")
    }

  property("Given any uppercase character, the diamond will end with 'A'") =
    forAll(genChar) { uc: UpperChar =>
      upCharDiamondLines(uc).last.contains("A")
    }

  property("The widest point will only have the supplied letter and inChar in between") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val ch = ucwa.value
      val lines = upCharWithoutADiamondLines(ucwa)
      val ((wline, _), windex) = lines.map(l => (l, l.filterNot(_ == outChar).length)).zipWithIndex.maxBy(_._1._2)

      ((wline.count(_ == ch) ?= 2) :| s"> EXPECTED: 2 '$ch'\n> GOT: $wline") && 
        wline.filterNot(c => c == ch || c == inChar).isEmpty :| s"expected only inChar other than [$ch] but got [$wline]"
    }

  property("Any line but the first and last will only have one repeated letter and inChar and possible outChar") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val lines = upCharWithoutADiamondLines(ucwa)
      val remaining = lines.tail.init //drop 'A's
      val rangeWithoutA = ('B' to 'Z')

      all(
        remaining.map { l =>
          val unique = l.toSet
          (removeInAndOut(l).length == 2 &&
          unique.size >= 2 && unique.size <= 3 && //may not have an outchar as in the widest line
          unique.contains(inChar) && //must have inChar
          unique.exists(rangeWithoutA.contains)) :| s"> LINE: $l"
        }
      )
    }

  property("Characters will be listed in ascending order until given letter") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val lines = upCharWithoutADiamondLines(ucwa)
      val uniqueChars = lines.map(removeInAndOut(_).head).slice(0, lines.length / 2)
      uniqueChars.zip(uniqueChars.tail).forall { case (c1, c2) => c1 < c2 }
    }

  property("The lines above the widest line will be a mirror image of the lines below it") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val lines = upCharWithoutADiamondLines(ucwa)
      val (_, windex) = lines.map(_.filterNot(_ == outChar).length).zipWithIndex.maxBy(_._1)
      val before = lines.slice(0, windex).toList
      val after  = lines.slice(windex + 1, lines.length).reverse.toList
      (before == after) :| s"> ACTUAL: [${before.mkString(",")}]\n> EXPECTED: [${after.mkString(",")}]"
    }

  property("Each line should have two inChar more than the line above it until widest line") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val lines  = upCharWithoutADiamondLines(ucwa).map(_.filterNot(_ == outChar))
      val mid    = lines.length / 2 //always odd
      val above  = lines.slice(0, mid + 1)

      val linesWithInCharCount = above.tail.map(_.count(_ == inChar))
      linesWithInCharCount.zip(linesWithInCharCount.tail).forall{
        case (first, second) => second - first == 2
      }
    }

  property("Each line should have two outChar less than the line above it until widest line") =
    forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
      val lines = upCharWithoutADiamondLines(ucwa)
      val linesWithOutCharCount = lines.map(_.filter(_ == outChar).length).slice(0, lines.length / 2)
      linesWithOutCharCount.zip(linesWithOutCharCount.tail).forall { case (s1, s2) => s1 - s2 == 2 }
    }

  property("Each line should be a mirror image of half itself") =
   forAll(genCharWithoutA) { ucwa: UpperCharWithoutA =>
     val lines = upCharWithoutADiamondLines(ucwa)
     all(
       lines.map {
         case l =>
           val length = l.length
           val half   = length / 2
           val left   = l.slice(0, half) //split each line down the middle
           val right  = l.slice(half + 1, length)
           (left == right.reverse) :| s"> LEFT: $left\n> RIGHT: $right"
       }
     )
   }

  property("The number of lines should be the length of the any line") =
   forAll(genChar) { uc: UpperChar =>
     val lines = upCharDiamondLines(uc)
     forAll(Gen.choose(0, lines.length - 1)) { index: Int =>
       lines(index).length ?= lines.length
     }
   }
}