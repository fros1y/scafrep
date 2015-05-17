/**
 * Created by martin on 4/28/15.
 */

package scafrep.Booleans

import scafrep.FReps._
import scafrep._


case class Union(val a: FRep, val b: FRep) extends FRep {
  def f(c: Coordinate) = {
    val fa = a.f(c)
    val fb = b.f(c)
    fa + fb + math.sqrt(fa * fa + fb * fb)
  }
}

case class Intersection(val a: FRep, val b: FRep) extends FRep {
  def f(c: Coordinate) = {
    val fa = a.f(c)
    val fb = b.f(c)
    fa + fb - math.sqrt(fa * fa + fb * fb)
  }
}

case class BlendUnion(val f1: FRep, val f2: FRep, val a0: Double, val a1: Double, val a2: Double) extends FRep {
  def f(c: Coordinate) = {
    val f1c = f1.f(c)
    val f2c = f2.f(c)
    val f1a1 = math.pow(f1c / a1, 2)
    val f2a2 = math.pow(f2c / a2, 2)

    f1c + f2c + math.sqrt(f1c * f1c + f2c * f2c) + a0 / (1 + f1a1 + f2a2)
  }
}

case class BlendIntersect(val f1: FRep, val f2: FRep, val a0: Double, val a1: Double, val a2: Double) extends FRep {
  def f(c: Coordinate) = {
    val f1c = f1.f(c)
    val f2c = f2.f(c)
    val f1a1 = math.pow(f1c / a1, 2)
    val f2a2 = math.pow(f2c / a2, 2)

    f1c + f2c - math.sqrt(f1c * f1c + f2c * f2c) + a0 / (1 + f1a1 + f2a2)
  }
}
