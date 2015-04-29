/**
 * Created by martin on 4/28/15.
 */

package scafrep.Booleans
import scafrep.FReps._
import scafrep._


case class Union(val a: FRep, val b: FRep) extends FRep {
  def evaluate(c: Coordinate, epsilon: Double): Geometry = a.evaluate(c, epsilon) || b.evaluate(c, epsilon)
}

case class Intersection(val a: FRep, val b: FRep) extends FRep {
  def evaluate(c: Coordinate, epsilon: Double): Geometry = a.evaluate(c, epsilon) && b.evaluate(c, epsilon)
}

case class Difference(val a: FRep, val b: FRep) extends FRep {
  def evaluate(c: Coordinate, epsilon: Double): Geometry = a.evaluate(c, epsilon) - b.evaluate(c, epsilon)
}
