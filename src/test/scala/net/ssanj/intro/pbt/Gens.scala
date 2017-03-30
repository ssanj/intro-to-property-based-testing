package net.ssanj.intro.pbt

import org.scalacheck.Arbitrary
import org.scalacheck.Gen

object Gens {
  def genWord: Gen[String] = for {
    w    <- Gen.choose(5, 10)
    word <- Gen.listOfN(w, Gen.frequency(1 -> Gen.alphaUpperChar, 1 -> Gen.alphaLowerChar))
  } yield word.mkString

  def genWordStr: Gen[String] = for {
    w     <- Gen.choose(2, 5)
    words <- Gen.listOfN(w, genWord)
  } yield words.mkString(" ")

  def genString: Gen[String] = Gen.frequency(
      1 -> Arbitrary.arbitrary[String],
      1 -> Gen.alphaStr,
      1 -> Gen.numStr,
      1 -> Gen.alphaNumStr,
      1 -> genWordStr
  )
}