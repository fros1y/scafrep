/**
 * Created by martin on 4/27/15.
 */

import scafrep._
import FReps._
import Booleans._
import Transform._

object main extends App {
  val space = new Space()

  val scene = CylinderX(10,30)
  //val scene = RotateZ(Box(-10,10,-10,10,-10,10), math.Pi/4)
  //val scene = Union(Sphere(20), Translate(Sphere(20),Coordinate(10,10,0)))
  //val scene = Intersection(Sphere(20), Translate(Sphere(20), Coordinate(10,10,0)))
  //val scene = Difference(Sphere(20), Translate(Sphere(20), Coordinate(10,10,0)))
  //val scene = Sphere(8)
  space.to_pointcloud(scene, "out.xyz")
}
