/**
 * Created by martin on 4/29/15.
 */

package scafrep.Transform
import scafrep.FReps._
import scafrep._

case class Translate(val fRep: FRep, val shift: Coordinate) extends FRep {
  def f(c: Coordinate) = fRep.f(c+shift)
}

case class Identity(val fRep: FRep) extends FRep {
  def f(c: Coordinate) = fRep.f(c)
}

//case class Rotation(val fRep: FRep, val axis: Coordinate, angle: Double) {
//  lazy val rotationMatrix =
//  def evaluate(c: Coordinate, epsilon: Double): Geometry = fRep.evaluate(c.transform(rotationMatrix), epsilon)
//
//}

////translate by a b c
// 1 0 0 a
// 0 1 0 b
// 0 0 1 c
// 0 0 0 1

