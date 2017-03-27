package net.ssanj.intro.pbt

import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.{Prop, Gen}
// import org.scalacheck.Prop.BooleanOperators

object ToUpperCaseProps extends Properties("ToUpperCase") with ToUpperCase {

  //val toUpperCase = scalaToUpper _
  // val toUpperCase = brokenToUpper _
  // val toUpperCase = filterOutLowerCase _
  // val toUpperCase = swappedToUpper _
  // val toUpperCase = filterOutNonLowerCase _
  val toUpperCase = asciiToUpper _

  private def genWord: Gen[String] = for {
    w    <- Gen.choose(5, 10)
    word <- Gen.listOfN(w, Gen.frequency(1 -> Gen.alphaUpperChar, 1 -> Gen.alphaLowerChar))
  } yield word.mkString

  private def genWordStr: Gen[String] = for {
    w     <- Gen.choose(2, 5)
    words <- Gen.listOfN(w, genWord)
  } yield words.mkString(" ")

  private def genString: Gen[String] = Gen.frequency(
      1 -> Arbitrary.arbitrary[String],
      1 -> Gen.alphaStr,
      1 -> Gen.numStr,
      1 -> Gen.alphaNumStr,
      1 -> genWordStr
  )

  property("String length is invariant") = Prop.forAll(genString) { string: String =>
    string.length == toUpperCase(string).length
  }

  property("All lowercase characters must be made uppercase") = Prop.forAll(genString) { string: String =>
    val lowerCaseCharsWithIndex = string.zipWithIndex.filter{ case (c, i) => c >= 'a' && c <= 'z' }
    val upperCasedString = toUpperCase(string)

    lowerCaseCharsWithIndex.forall{ case (c, i) =>
      val mapped = upperCasedString(i)
      mapped.toInt == (c - 32).toInt // how can we verify this without reimplementing toUpper?
    }
  }

  property("All non lowercase characters must be at the same positions") = Prop.forAll(genString) { string: String =>
    val nonLowerCaseCharsWithIndex = string.zipWithIndex.filterNot{ case (c, i) => c >= 'a' && c <= 'z' }
    val upperCasedString = toUpperCase(string)

    nonLowerCaseCharsWithIndex.forall{ case (c, i) =>
      val mapped = upperCasedString(i)
      mapped.toInt == c
    }
  }



  //What are the requirements of toUpperCase?
  //1. All lowercase characters must be made uppercase. - How can we check if something is uppercase?
  //2. All non-lowercase characters should be remain unchanged.
  //3. The length of the String should remain unchanged.
  }