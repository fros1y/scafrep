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
