package net.ssanj.intro.pbt

trait Addition {

  //default
  def scalaAdd(x: Int, y: Int): Int = x + y

  //use left param
  def leftAdd(x: Int, y: Int): Int = x

  //use right param
  def rightAdd(x: Int, y: Int): Int = y

  //return zero
  def zeroAdd(x: Int, y: Int): Int = 0

  //change result if x is less than zero
  def negAdd(x: Int, y: Int): Int = if (x < 0) Math.abs(x) + y else x + y

  //change result if x plus y is 42
  def hitchAdd(x: Int, y: Int): Int = if ((x + y) == 42) y - x else x + y
}