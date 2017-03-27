package net.ssanj.intro.pbt

trait ToUpperCase {

  //handles ascii and others
  def scalaToUpper(value: String): String = value.toUpperCase

  //handles only ascii
  def asciiToUpper(value: String): String = value.map {
    case c if c.toInt >= 97 && c.toInt <= 122 => (c - 32).toChar
    case c => c
  }

  //drops the 'z'
  def brokenToUpper(value: String): String = value.map {
    case c if c.toInt >= 97 && c.toInt <= 121 => (c - 32).toChar
    case c => c
  }

  //swaps a -> Z and z -> A
  def swappedToUpper(value: String): String = value.map {
    case c if c.toInt >= 97 && c.toInt <= 122 =>
      val upper    = c.toInt - 32
      val delta    = 'Z'.toInt - upper
      val newUpper = 'A'.toInt + delta
      newUpper.toChar
    case c => c
  }

  //filters out lowercase characters
  def filterOutLowerCase(value: String): String = value.filterNot(c => c >= 97 && c <= 122)

  //filters out non-lowercase characters
  def filterOutNonLowerCase(value: String): String = value.map(c => if (c < 97 && c > 122) '?' else c.toUpper)
}