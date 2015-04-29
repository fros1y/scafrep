/**
 * Created by martin on 4/29/15.
 */

package scafrep.Transform
import scafrep.FReps._
import scafrep._

case class Translate(val fRep: FRep, val shift: Coordinate) extends FRep {
  def evaluate(c: Coordinate, epsilon: Double): Geometry = fRep.evaluate(c + shift, epsilon)
}
