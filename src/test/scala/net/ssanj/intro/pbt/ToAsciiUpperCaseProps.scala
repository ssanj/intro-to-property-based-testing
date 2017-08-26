package net.ssanj.intro.pbt

import org.scalacheck.{Prop, Properties}
import org.scalacheck.Prop._

/**
 * What are the requirements of toUpperCase?
 *
 * 1. All lowercase characters must be made uppercase.
 * 2. All non-lowercase characters should be remain in the same case.
 * 3. The length of the String should remain unchanged. (No characters dropped or added)
 * 4. All characters should retain their original position before and after uppercasing.
 *
 */

object ToUpperCaseProps extends Properties("ToUpperCase") with ToAsciiUpperCase {

  import Gens._

  // val toUpperCase = scalaToUpper _
  // val toUpperCase = brokenToUpper _
  // val toUpperCase = filterOutLowerCase _
  // val toUpperCase = swappedToUpper _
  // val toUpperCase = filterOutNonLowerCase _
  val toUpperCase = asciiToUpper _

  private def all(propSeq: Seq[Prop]): Prop = Prop.all(propSeq:_*)

  property("All lowercase characters must be made uppercase") =
    Prop.forAll(genString) { string: String =>
      val lowerCaseCharsWithIndex = string.zipWithIndex.filter{ case (c, i) => c >= 'a' && c <= 'z' }
      val upperCasedString = toUpperCase(string)

      val properties = lowerCaseCharsWithIndex.map { case (c, i) =>
        val mappedChar = upperCasedString(i)
        (mappedChar >= 'A' && mappedChar <= 'Z') :|
          s"${string}(${i}) == '${c}' is not UpperCase: ${mappedChar}"
      }

      all(properties)
    }

  property("All characters should retain their order") =
    Prop.forAll(genString) { string: String =>
      val uppedLower    = toUpperCase(string).toLowerCase
      val originalLower = string.toLowerCase
      uppedLower ?= originalLower
    }

  property("All non lowercase characters must be at the same positions") =
    Prop.forAll(genString) { string: String =>
      val nonLowerCaseCharsWithIndex = string.zipWithIndex.filterNot{ case (c, i) => c >= 'a' && c <= 'z' }
      val upperCasedString = toUpperCase(string)

      val properties = nonLowerCaseCharsWithIndex.map { case (c, i) =>
        val originalChar = upperCasedString(i)
        (originalChar == c) :| s"[${originalChar},${i}] != [${c}]"
      }

      all(properties)
    }

    property("should not change length") = {
        Prop.forAll(genString) {  string: String =>
          val upped = toUpperCase(string)
          (string.length =? upped.length) :|
            s"length before uppercase: ${string.length}, after: ${upped.length}"
        }
    }
  }