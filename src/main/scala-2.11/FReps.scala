/**
 * Created by martin on 4/28/15.
 */
package scafrep.FReps
import scafrep._
import scafrep.BoundaryPositions._

trait FRep {
  def evaluate(c: Coordinate, epsilon: Double): Geometry
}

case class Sphere(val radius: Double = 10) extends FRep {
  def evaluate(c: Coordinate, epsilon: Double = 0.5): Geometry = {
    if (c.length() > radius) {
      Geometry(OUTSIDE)
    } else if (radius - c.length() < epsilon) {
      Geometry(SURFACE)
    } else {
      Geometry(INSIDE)
    }
  }
}


//object FReps {
//  type FRep = Coordinate => Geometry
//
//  def Sphere(radius: Double = 10, epsilon: Double = 0.5): FRep = {
//    def _sphere(c: Coordinate): Geometry = {
//      if (c.length() > radius) {
//        Geometry(OUTSIDE)
//      } else if (radius - c.length() < epsilon) {
//        Geometry(SURFACE)
//      } else {
//        Geometry(INSIDE)
//      }
//    }
//    _sphere
//  }
//
//  def Cylinder(height: Double = 10, radius: Double = 1, epsilon: Double = 0.5): FRep = {
//    def _cylinder(c: Coordinate): Geometry = {
//      import math._
//      val distance = sqrt(pow(c.x, 2) + pow(c.y, 2))
//      if (c.z < 0 || c.z > height || distance > radius) Geometry(OUTSIDE)
//      else if (c.z < 0 + epsilon ||
//        c.z > height - epsilon ||
//        distance > radius - epsilon)
//        Geometry(SURFACE)
//      else Geometry(OUTSIDE)
//    }
//    _cylinder
//  }
//
//}
//
