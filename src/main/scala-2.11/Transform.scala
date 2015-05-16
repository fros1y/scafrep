/**
 * Created by martin on 4/29/15.
 */

package scafrep.Transform
import scafrep.FReps._
import scafrep._

case class Translate(val fRep: FRep, val shift: Coordinate) extends FRep {
  def f(c: Coordinate) = fRep.f(c+shift)
}

case class Scale(val fRep: FRep, val xscale: Double = 2, val yscale: Double = 2, val zscale: Double = 2) extends FRep{
  def f(c: Coordinate) = fRep.f(Coordinate(c.x/xscale,c.y/yscale,c.z/zscale))
}

case class RotateZ(val fRep: FRep, val radians: Double = 0) extends FRep {
  def f(c: Coordinate) = {
    val xprime = c.x * math.cos(radians) + c.y * math.sin(radians)
    val yprime = c.y * math.cos(radians) - c.x * math.sin(radians)
    fRep.f(Coordinate(xprime, yprime, c.z))
  }
}

case class RotateY(val fRep: FRep, val radians: Double = 0) extends FRep {
  def f(c: Coordinate) = {
    val xprime = c.x * math.cos(radians) - c.z * math.sin(radians)
    val zprime = c.x * math.cos(radians) + c.z * math.sin(radians)
    fRep.f(Coordinate(xprime, c.y, zprime))
  }
}

case class RotateX(val fRep: FRep, val radians: Double = 0) extends FRep {
  def f(c: Coordinate) = {
    val yprime = c.y * math.cos(radians) + c.z * math.sin(radians)
    val zprime = c.z * math.cos(radians) - c.y * math.sin(radians)
    fRep.f(Coordinate(c.x, yprime, zprime))
  }
}

case class Identity(val fRep: FRep) extends FRep {
  def f(c: Coordinate) = fRep.f(c)
}
