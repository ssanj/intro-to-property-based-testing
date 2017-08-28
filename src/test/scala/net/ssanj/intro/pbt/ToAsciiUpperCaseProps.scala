package net.ssanj.intro.pbt

import org.scalacheck.{Prop, Properties}
import org.scalacheck.Prop._

/**
 * What are the requirements of toUpperCase?
 * 1. The length of the String should remain unchanged.
 * 2. All lowercase characters must be converted to uppercase.
 * 3. All lowercase characters must correspond to their uppercase equivalents.
 * 4. All lowercase characters must be uppercased in the same locations.
 * 5. All non-lowercase characters should be remain unchanged in the same locations.
 * 6. Uppercasing once should be the same as uppercasing twice.
 */

object ToAsciiUpperCaseProps extends Properties("ToUpperCase") with ToAsciiUpperCase {

  import Gens._

  // val toUpperCase = noOpToUpper _
  // val toUpperCase = emptyStringToUpper _
  // val toUpperCase = skipLowerYandZToUpper _
  // val toUpperCase = transposeLowerToUpper _
  // val toUpperCase = transposeNonLowerToUpper _
  // val toUpperCase = filterOutLowerCase _
  // val toUpperCase = filterOutNonLowerCase _
  // val toUpperCase = addCharsToUpper _
  val toUpperCase = asciiToUpper _
  // val toUpperCase = scalaToUpper _

  private def all(propSeq: Seq[Prop]): Prop = Prop.all(propSeq:_*)

  property("should not change length") = {
      Prop.forAll(genString) {  string: String =>
        val upped = toUpperCase(string)
        (string.length =? upped.length) :|
          s"length before uppercase: ${string.length}, after: ${upped.length}"
      }
  }

  property("All lowercase characters must be made uppercase") =
    Prop.forAll(genString) { string: String =>
      val lowerCaseCharsWithIndex = string.zipWithIndex.filter{ case (c, i) => isAsciiLower(c) }
      val upperCasedString = toUpperCase(string)

      val properties = lowerCaseCharsWithIndex.map { case (c, i) =>
        val mappedChar = upperCasedString(i)
        isAsciiUpper(mappedChar) :|
          s"${string}(${i}) == '${c}' is not UpperCase: ${mappedChar}"
      }

      all(properties)
    }

  property("All lowercase characters must correspond to their uppercase equivalents") =
      Prop.forAll(genString) { string: String =>
        val lowerCaseCharsWithIndex = string.zipWithIndex.filter{ case (c, i) => isAsciiLower(c) }
        val upped = toUpperCase(string)

        val properties = lowerCaseCharsWithIndex.map {
          case (c, i) => c - 'a' ?= upped(i) - 'A'
        }

        all(properties)
      }

  property("All lowercase characters must be uppercased in the same locations") =
    Prop.forAll(genString) { string: String =>
      val uppedLower    = toUpperCase(string).toLowerCase
      val originalLower = string.toLowerCase
      uppedLower ?= originalLower
    }

  property("All non-lowercase characters should be remain unchanged in the same locations") =
    Prop.forAll(genString) { string: String =>
      val nonLowerCaseCharsWithIndex = string.zipWithIndex.filterNot{ case (c, i) => isAsciiLower(c) }
      val upperCasedString = toUpperCase(string)

      val properties = nonLowerCaseCharsWithIndex.map { case (c, i) =>
        val originalChar = upperCasedString(i)
        (originalChar == c) :| s"[${originalChar},${i}] != [${c}]"
      }

      all(properties)
    }

  property("Uppercasing once should be the same as uppercasing twice") =
    Prop.forAll(genString) { string: String =>
      toUpperCase(string) ?= toUpperCase(toUpperCase(string))
    }
  }