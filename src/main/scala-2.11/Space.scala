/**
 * Created by martin on 4/28/15.
 */
package scafrep

import FReps._
import BoundaryPositions._

import scala.annotation.tailrec
import scala.collection.mutable.{ListBuffer, ArrayBuffer}

class Space(val xmin: Double = -50, val xmax: Double = 50,
            val ymin: Double = -50, val ymax: Double = 50,
            val zmin: Double = -50, val zmax: Double = 50) {

  def surfacePoints(fRep: FRep) = {
    findSurface(fRep, grid(xmin, xmax, ymin, ymax, zmin, zmax, 1).map(fRep.evaluate(_)), 1).map(_.c)
  }

  def normal(fRep: FRep, p: Coordinate, d: Double=0.0001): Coordinate = {
    val f = fRep.f(p)
    val dfdx = (fRep.f(p+new Coordinate(d,0,0))-f)/d
    val dfdy = (fRep.f(p+new Coordinate(0,d,0))-f)/d
    val dfdz = (fRep.f(p+new Coordinate(0,0,d))-f)/d
    val raw = new Coordinate(dfdx, dfdy, dfdz)
    raw / -raw.length()
  }

  def to_pointcloud(fRep: FRep, filename: String): Unit = {
    import java.io._
    println("starting pointcloud")
    val out = new PrintWriter(filename)
    surfacePoints(fRep).foreach(p => {
      out.write(p.toString() + " " + normal(fRep, p) + "\n")
    })
    out.close()
    println("done with pointcloud")
  }

  def grid(xmin: Double, xmax: Double, ymin: Double, ymax: Double, zmin: Double, zmax: Double, step: Double): Seq[Coordinate] = {
    val halfStep = step / 2
    for {
      mx <- (xmin + halfStep) to xmax by step
      my <- (ymin + halfStep) to ymax by step
      mz <- (zmin + halfStep) to zmax by step
    } yield new Coordinate(mx, my, mz)
  }

  @tailrec
  final def findSurface(fRep: FRep, coordinates: Seq[EPoint], step: Double): Seq[EPoint] = {
    val newStep = step / 2

    def _refinePoint(e: EPoint, refineStep: Double): Seq[EPoint] = {
      val halfStep = step / 2
      val threshold = step //math.sqrt(step * step)
      if (math.abs(e.value) > threshold) List()
      //else if (math.abs(e.value) < 0.05) List(e)
      else grid(e.c.x - halfStep,
        e.c.x + halfStep,
        e.c.y - halfStep,
        e.c.y + halfStep,
        e.c.z - halfStep,
        e.c.z + halfStep,
        refineStep).map(fRep.evaluate(_))
    }

    if (step < 0.25)
      coordinates.filter(_.within(.005))
    else
      findSurface(fRep, coordinates.flatMap(_refinePoint(_, newStep)), newStep)
  }
}
