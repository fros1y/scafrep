/**
 * Created by martin on 4/28/15.
 */
package scafrep.FReps
import scafrep._
import Booleans._

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

case class _GtX(val x: Double) extends FRep {
  def f(c: Coordinate): Double = c.x - x
}

case class _LtX(val x: Double) extends FRep {
  def f(c: Coordinate): Double = x - c.x
}
case class _GtY(val y: Double) extends FRep {
  def f(c: Coordinate): Double = c.y - y
}

case class _LtY(val y: Double) extends FRep {
  def f(c: Coordinate): Double = y - c.y
}
case class _GtZ(val z: Double) extends FRep {
  def f(c: Coordinate): Double = c.z - z
}
case class _LtZ(val z: Double) extends FRep {
  def f(c: Coordinate): Double = z - c.z
}

case class Box( val xmin: Double = -5, val xmax: Double = 5,
                val ymin: Double = -5, val ymax: Double = 5,
                val zmin: Double = -5, val zmax: Double = 5)
          extends FRep
{
  val ltx = _LtX(xmax)
  val gtx = _GtX(xmin)
  val x = Intersection(ltx,gtx)
  val lty = _LtY(ymax)
  val gty = _GtY(ymin)
  val y = Intersection(lty, gty)
  val ltz = _LtZ(zmax)
  val gtz = _GtZ(zmin)
  val z = Intersection(ltz, gtz)
  val xy = Intersection(x,y)
  def f(c: Coordinate): Double = Intersection(xy, z).f(c)
}

case class InfCylinderX(val radius: Double) extends FRep {
  def f(c:Coordinate): Double = radius*radius - c.y*c.y - c.z*c.z
}

case class CylinderX(val radius: Double, val height: Double) extends FRep {
  val infcylinderx = InfCylinderX(radius)
  val ltx = _LtX(height/2)
  val gtx = _GtX(-height/2)
  val x = Intersection(ltx, gtx)
  def f(c: Coordinate): Double = Intersection(infcylinderx, x).f(c)
}

case class TorusX(val radius: Double, val revolution: Double) extends FRep {
  def f(c: Coordinate): Double = math.pow(revolution - math.sqrt(c.x*c.x + c.y*c.y),2) + c.z*c.z - radius*radius
    //radius*radius - c.y*c.y - c.z*c.z - revolution*revolution + 2*revolution*math.sqrt(c.z*c.z+c.y*c.y)
}