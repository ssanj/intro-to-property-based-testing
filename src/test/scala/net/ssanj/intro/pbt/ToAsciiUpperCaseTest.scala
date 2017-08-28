package net.ssanj.intro.pbt

import org.scalatest.{Matchers, WordSpecLike}

final class ToAsciiUpperCaseTest extends Matchers with WordSpecLike with ToAsciiUpperCase {

  // val toUpperCase = noOpToUpper _
  // val toUpperCase = emptyStringToUpper _
  // val toUpperCase = skipLowerYandZToUpper _
  // val toUpperCase = transposeLowerToUpper _
  // val toUpperCase = transposeNonLowerToUpper _
  // val toUpperCase = filterOutLowerCase _
  // val toUpperCase = filterOutNonLowerCase _
  // val toUpperCase = addCharsToUpper _
  // val toUpperCase = asciiToUpper _
  val toUpperCase = scalaToUpper _

  "ToUpperCase" should {
    "make a lowercase word upper case" when {
      "given a lower case word" in {
        toUpperCase("this is lowercase") should be ("THIS IS LOWERCASE")
      }
    }

    "make a mixed case word upper case" when {
      "given a mixed case word" in {
        toUpperCase("This iS a Mix Of CaSes") should be ("THIS IS A MIX OF CASES")
      }
    }

    "not change word" when {
      "given an upper case word" in {
        toUpperCase("THIS IS UPPERCASE") should be ("THIS IS UPPERCASE")
      }
    }

    "not change unicode characters" when {
      "given an unicode words" in {
        val unicodeWord = ('\u1230' to '\u1240').mkString
          toUpperCase(unicodeWord) should be (unicodeWord)
      }
    }

    "make only letters upper case" when {
      "given a word that has numbers and symbols" in {
        toUpperCase("This hAs numbers: 12345!") should be ("THIS HAS NUMBERS: 12345!")
      }
    }
  }
}
