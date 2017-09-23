package net.ssanj.intro.pbt

/**
 * Various breakages are commented out. Put them back in and comment out the default
 * implementation to verify that DiamondProps and DiamondTest fail as expected.
 */

trait DiamondBroken {

  private val start = 'A'

  val inChar = '*'

  val outChar = '-'

  val inString = inChar.toString

  val outString = outChar.toString

  def instances(supplied: Char): Int = if (supplied == start) 1 else 2

 // def instances(supplied: Char): Int = if (supplied == start) 1 else 3 //1. add extra instances

  def betweenSpace(supplied: Char): Int = {
    val diff = supplied - start
    if (diff <= 1) diff else (2 * diff) - 1
  }

  def outerSpace(supplied: Char, current: Char): Int = supplied - current

  def previousChars(supplied: Char): Seq[Char] = (start until supplied)

  // def previousChars(supplied: Char): Seq[Char] = (start until supplied).reverse //2. reverse order

  def printChar(supplied: Char, current: Char): String = {
    val inside  = inString * betweenSpace(current)
    // val inside  = { //3. insert another char in the middle
    //   val spaces = betweenSpace(current)
    //   val halfMinusMiddle = spaces / 2
    //   // (inString * halfMinusMiddle) ++ "@" ++ (inString * halfMinusMiddle) //3.1 different char
    //   (inString * halfMinusMiddle) ++ outString ++ (inString * halfMinusMiddle) //3.2 outString
    // }
    val outside = outString * outerSpace(supplied, current)
    val values  = (1 to instances(current)).map(_ => current)

    // values.mkString(outside, inside, "") //4.1 remove outside space
    // values.mkString(outside, "", outside) //4.2 remove inside space
    values.mkString(outside, inside, outside)
  }

  def printDiamond(supplied: Char): String = {
    if (supplied == start || !('A' to 'Z').contains(supplied)) start.toString
    else /*if (('B' to 'T').contains(supplied))*/ { //5. only work for values from 'B' -> 'T'.
      val chars  = previousChars(supplied) //A
      val before = chars.foldLeft(Seq.empty[String])((acc, c) => acc :+ printChar(supplied, c))
      val after  = before.reverse
      // val after  = before //6. don't reverse
      val line   = printChar(supplied, supplied)
      (before ++ (line +: after)).mkString("\n")
    } //else { "A\nA" } //5. return default
  }
}
