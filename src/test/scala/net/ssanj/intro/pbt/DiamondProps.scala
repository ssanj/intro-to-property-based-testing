package net.ssanj.intro.pbt

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop._

/**
 * What the properties of a Diamond?
 *
 * 1. Given a letter, the diamond will start with 'A'
 * 2. Given a letter, the diamond will end with 'A'
 * 3. The widest point will only have the supplied letter and spaces
 * 4. Characters will be listed in ascending order until given letter and then descend
 * 5. The lines above the widest line will be a mirror image of the lines below it
 * 6. Each line should have one space more inbetween chars than the line above it
 * 7. Each line should have one space less outside chars than the line above it
 * 8. All unhandled inputs should return 'A'
 * 9. The number of lines should be (char - 'A') + (char - A + 1)
 */

object DiamondProps extends Properties("Diamond") with Diamond {

 private def genCharWithoutA: Gen[Char] = Gen.choose('B', 'Z')

  property("Given a letter, the diamond will start with 'A'") =
    forAllNoShrink(Gen.alphaUpperChar) { ch: Char =>
      printDiamond(ch).split("\n").head.contains("A")
    }

  property("Given a letter, the diamond will end with 'A'") =
    forAllNoShrink(Gen.alphaUpperChar) { ch: Char =>
      printDiamond(ch).split("\n").last.contains("A")
    }

  property("The widest point will only have the supplied letter and spaces") =
    forAllNoShrink(genCharWithoutA) { ch: Char =>
      val lines = printDiamond(ch).split("\n")
      val ((wline, _), windex) = lines.map(l => (l, l.trim.length)).zipWithIndex.maxBy(_._1._2)

      ((wline.count(_ == ch) ?= 2) :| s"expected 2 [$ch] but got [$wline]") && wline.filterNot(_ == ch).trim.isEmpty :| s"expected only spaces other than [$ch] but got [$wline]"
    }

  property("The lines above the widest line will be a mirror image of the lines below it") =
    forAllNoShrink(genCharWithoutA) { ch: Char =>
      val lines = printDiamond(ch).split("\n")
      val (_, windex) = lines.map(_.trim.length).zipWithIndex.maxBy(_._1)
      val before = lines.slice(0, windex).toList
      val after  = lines.slice(windex + 1, lines.length).reverse.toList
      (before == after) :| s"actual: [${before.mkString(",")}], expected: [${after.mkString(",")}]"
    }
}