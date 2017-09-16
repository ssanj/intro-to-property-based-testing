package net.ssanj.intro.pbt

/**
 * http://claysnow.co.uk/recycling-tests-in-tdd/
 */

trait Diamond {

  private val start = 'A'

  val inChar = '*'

  val outChar = '-'

  val inString = inChar.toString

  val outString = outChar.toString

  def instances(supplied: Char): Int = if (supplied == start) 1 else 2

  def betweenSpace(supplied: Char): Int = {
    val diff = supplied - start
    if (diff <= 1) diff else (2 * diff) - 1
  }

  def outerSpace(supplied: Char, current: Char): Int = supplied - current

  def previousChars(supplied: Char): Seq[Char] = (start until supplied)

  def printChar(supplied: Char, current: Char): String = {
    val inside  = inString  * betweenSpace(current)
    val outside = outString * outerSpace(supplied, current)
    val values  = (1 to instances(current)).map(_ => current)

    values.mkString(outside, inside, outside)
  }

  def printDiamond(supplied: Char): String = {
    if (supplied == start || !('A' to 'Z').contains(supplied)) start.toString
    else {
      val chars  = previousChars(supplied) //A
      val before = chars.foldLeft(Seq.empty[String])((acc, c) => acc :+ printChar(supplied, c))
      val after  = before.reverse
      val line   = printChar(supplied, supplied)
      (before ++ (line +: after)).mkString("\n")
    }
  }
}
