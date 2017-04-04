package net.ssanj.intro.pbt

import org.scalacheck._
import org.scalacheck.Prop._

object SmallerAdditionProps extends Properties("Addition") with Addition {

  import Gens._

  val add = scalaAdd _
  // val add = leftAdd _
  // val add = rightAdd _
  // val add = zeroAdd _
  // val add = negAdd _
  // val add = hitchAdd _

  property("commutative") = Prop.forAll(genSmallerNumberPairs(100)) { case ((n1: Int, n2: Int)) =>
    Prop.collect(s"n1:${n1}, n2:${n2}") {
      add(n1, n2) ?= add(n2, n1)
    }
  }

  property("left identity") = Prop.forAll(genSmallerNumber(100)) { n: Int =>
    add(0, n) ?= n
  }

  property("right identity") = Prop.forAll(genSmallerNumber(100)) { n: Int =>
    add(n, 0) ?= n
  }

  property("distribution") = Prop.forAll(genSmallerNumberTriple(100)) { case ((n1: Int, n2: Int, nx: Int)) =>
    add(n1, n2) * nx == add(nx * n1, nx * n2)
  }

  property("associativity") = Prop.forAll(genSmallerNumberTriple(100)) { case ((n1: Int, n2: Int, n3: Int)) =>
    add(add(n1, n2), n3) == add(n1, add(n2, n3))
  }
}