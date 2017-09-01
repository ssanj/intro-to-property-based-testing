package net.ssanj.intro.pbt

/**
 * Various breakages
 */

trait DiamondBroken {

  private val start = 'A'

  def instances(supplied: Char): Int = if (supplied == start) 1 else 2

//  def instances(supplied: Char): Int = if (supplied == start) 1 else 3 //add extra instances

  def betweenSpace(supplied: Char): Int = {
    val diff = supplied - start
    if (diff <= 1) diff else (2 * diff) - 1
  }

  def outerSpace(supplied: Char, current: Char): Int = supplied - current

  def previousChars(supplied: Char): Seq[Char] = (start until supplied)

  def printChar(supplied: Char, current: Char): String = {
    val inside  = " " * betweenSpace(current)
    val outside = " " * outerSpace(supplied, current)
    val values  = (1 to instances(current)).map(_ => current)

    // values.mkString(outside, inside, "") //remove inside space
    // values.mkString(outside, "", outside) //remove outside space
    values.mkString(outside, inside, outside)
  }

  def printDiamond(supplied: Char): String = {
    if (supplied == start || !('A' to 'Z').contains(supplied)) start.toString
    else /*if (('B' to 'T').contains(supplied))*/ { //sub range
      val chars  = previousChars(supplied) //A
      val before = chars.foldLeft(Seq.empty[String])((acc, c) => acc :+ printChar(supplied, c))
      val after  = before.reverse
      // val after  = before.reverse //don't reverse
      val line   = printChar(supplied, supplied)
      (before ++ (line +: after)).mkString("\n")
    } //else { "A\nA" } //return default
  }
}
