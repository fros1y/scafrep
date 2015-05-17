/**
 * Created by martin on 4/27/15.
 */

import scafrep.Booleans._
import scafrep.FReps._
import scafrep.Transform._
import scafrep._

object main extends App {
  val space = new Space()
  //val scene = Sphere(30)
  //val scene = TorusX(15,30)
  //val scene = CylinderX(10,30)
  //val scene = RotateZ(Box(-10,10,-10,10,-10,10), math.Pi/4)
  //val scene = BlendUnion(Sphere(20), Translate(Sphere(10),Coordinate(20,20,0)),5,1,1)
  //val scene = Intersection(Sphere(20), Translate(Sphere(20), Coordinate(10,10,0)))
  val scene = Union(Sphere(20), Translate(Sphere(20), Coordinate(10, 10, 0)))

  def time(f: => Unit) = {
    val s = System.currentTimeMillis
    f
    println("Run took: " + (System.currentTimeMillis - s))
  }
  time(space.to_pointcloud(scene, "out.xyz"))
}
