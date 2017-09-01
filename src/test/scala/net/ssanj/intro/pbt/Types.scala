package net.ssanj.intro.pbt

import org.scalacheck.Shrink
import org.scalacheck.util.Pretty
import scala.language.implicitConversions


final case class UpperChar(value: Char)

object UpperChar {
  implicit def shrinkUpperChar: Shrink[UpperChar] =
    Shrink {
      case UpperChar(ch) => ('A' until ch).reverse.map(UpperChar(_)).toStream
    }

  implicit def prettyUpperChar(up: UpperChar): Pretty = Pretty(_ => s"'${up.value}'")
}

final case class UpperCharWithoutA(value: Char)

object UpperCharWithoutA {
  implicit def shrinkUpperCharWithoutA: Shrink[UpperCharWithoutA] =
    Shrink {
      case UpperCharWithoutA('A') => Stream.Empty
      case UpperCharWithoutA(ch) => ('B' until ch).reverse.map(UpperCharWithoutA(_)).toStream
    }

  implicit def prettyUpperCharWithoutA(upwa: UpperCharWithoutA): Pretty = Pretty(_ => s"'${upwa.value}'")

}