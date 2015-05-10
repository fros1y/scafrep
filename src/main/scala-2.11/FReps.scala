/**
 * Created by martin on 4/28/15.
 */
package scafrep.FReps
import scafrep._

case class EPoint(val c: Coordinate, val value: Double)
{
  def within(epsilon: Double = 0.1): Boolean = math.abs(value) < epsilon*2
  def insideBy(epsilon: Double = 0.05): Boolean = value > epsilon
}

trait FRep {
  def f(c: Coordinate): Double
  def evaluate(c: Coordinate): EPoint = EPoint(c, f(c))
}

case class Sphere(val radius: Double = 10) extends FRep {
  def f(c: Coordinate): Double = radius - c.length()
}
