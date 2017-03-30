package net.ssanj.intro.pbt

//Requirement: Convert ASCII characters to Uppercase variants
trait ToAsciiUpperCase {

  //default
  def scalaToUpper(value: String): String = value.toUpperCase

  //handles only ascii
  def asciiToUpper(value: String): String = value.map {
    case c if c >= 'a' && c <= 'z' => (c - 32).toChar
    case c => c
  }

  //drops the 'z'
  def brokenToUpper(value: String): String = value.map {
    case c if c.toInt >= 'a' && c.toInt <= 'y' => (c - 32).toChar
    case c => c
  }

  //swaps a -> Z and z -> A
  def swappedToUpper(value: String): String = value.map {
    case c if c.toInt >= 'a' && c.toInt <= 'z' =>
      val upper    = c - 32
      val delta    = 'Z' - upper
      val newUpper = 'A' + delta
      newUpper.toChar
    case c => c
  }

  //filters out lowercase characters
  def filterOutLowerCase(value: String): String = value.filterNot(c => c >= 'a' && c <= 'z')

  //filters out non-lowercase characters
  def filterOutNonLowerCase(value: String): String = value.map(c => if (c < 'a' || c > 'z') '?' else c.toUpper)
}