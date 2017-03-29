package net.ssanj.intro.pbt

trait Addition {

  def scalaAdd(x: Int, y: Int): Int = x + y

  def leftAdd(x: Int, y: Int): Int = x

  def rightAdd(x: Int, y: Int): Int = y

  def zeroAdd(x: Int, y: Int): Int = 0

  def negAdd(x: Int, y: Int): Int = if (x < 0) Math.abs(x) + y else x + y

  def hitchAdd(x: Int, y: Int): Int = if ((x + y) == 42) y -x else x + y
}