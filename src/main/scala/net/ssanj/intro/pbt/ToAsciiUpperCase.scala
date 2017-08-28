package net.ssanj.intro.pbt

//Requirement: Convert ASCII characters to Uppercase variants
trait ToAsciiUpperCase {

  def isAsciiLower(ch: Char): Boolean = ch >= 'a' && ch <= 'z'

  def isAsciiUpper(ch: Char): Boolean = ch >= 'A' && ch <= 'Z'

  private val rangeDiff = 32

  private def uppercaseChar(ch: Char): Char = (ch - rangeDiff).toChar

  //default
  def scalaToUpper(value: String): String = value.toUpperCase

  //handles only ascii
  def asciiToUpper(value: String): String = value.map {
    case c if isAsciiLower(c) => uppercaseChar(c)
    case c => c
  }

  //do nothing
  def noOpToUpper(value: String): String = value

  //return empty String
  def emptyStringToUpper(value: String): String = ""

  //skip  'y' and 'z'
  def skipLowerYandZToUpper(value: String): String = value.map {
    case c if c.toInt >= 'a' && c.toInt <= 'x' => uppercaseChar(c)
    case c => c
  }

  //swaps a -> Z and z -> A
  def transposeLowerToUpper(value: String): String = value.map {
    case c if isAsciiLower(c) =>
      val upper    = c - rangeDiff
      val delta    = 'Z' - upper
      val newUpper = 'A' + delta
      newUpper.toChar
    case c => c
  }

  //Change non lowercase characters
  def transposeNonLowerToUpper(value: String): String = value.map {
    case c if !isAsciiLower(c) => (c + 1).toChar
    case c => uppercaseChar(c)
  }

  //filters out lowercase characters
  def filterOutLowerCase(value: String): String = value.filterNot(isAsciiLower)

  //filters out non-lowercase characters
  def filterOutNonLowerCase(value: String): String = value.filter(isAsciiLower).map(_.toUpper)

  //add an extra symbol to the end
  def addCharsToUpper(value: String): String = value.map {
    case c if isAsciiLower(c) => uppercaseChar(c)
    case c => c
  } ++ "!"
}