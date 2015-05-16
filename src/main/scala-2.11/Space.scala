/**
 * Created by martin on 4/28/15.
 */
package scafrep
import FReps._
import BoundaryPositions._

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

class Space(val xmin: Double = -50, val xmax: Double = 50,
            val ymin: Double = -50, val ymax: Double = 50,
            val zmin: Double = -50, val zmax: Double = 50,
            val step: Double = .25) extends Iterable[Coordinate] {

  def iterator = new Iterator[Coordinate] {
    var x: Double = xmin
    var y: Double = ymin
    var z: Double = zmin

    def next(): Coordinate = {
      val nextCoord = new Coordinate(x, y, z)
      x+=step
      if(x > xmax) {
        y+=step
        x = xmin
      }
      if(y > ymax) {
        z+=step
        y = ymin
      }
      return nextCoord
    }
    def hasNext(): Boolean = z < zmax
  }

  def evaluation_points = iterator

  def surfacePoints(fRep: FRep) = {
    //evaluation_points.filter(fRep.evaluate(_).within(0.1))
    //iterativeSurfaceEval(fRep, .5).map(_.c)
    //recursiveEval(fRep, xmin, xmax, ymin, ymax, zmin, zmax, 1).filter(_.within(.05)).map(_.c)
    findSurface(fRep, grid(xmin,xmax,ymin,ymax,zmin,zmax,1).map(fRep.evaluate(_)),1,.1).map(_.c)
  }

  def to_pointcloud(fRep: FRep, filename: String): Unit = {
    import java.io._
    println("starting pointcloud")
    val out = new PrintWriter(filename)
    surfacePoints(fRep).foreach(p => {
      out.write(p.toString() + "\n")
    })
    out.close()
    println("done with pointcloud")
  }

  def recursiveEval(frep: FRep, xmin: Double, xmax: Double, ymin: Double, ymax: Double, zmin: Double, zmax: Double, step: Double): ListBuffer[EPoint] = {
    val halfStep = step / 2
    var results = new ListBuffer[EPoint]
    for {
      mx <- (xmin + halfStep) to xmax by step
      my <- (ymin + halfStep) to ymax by step
      mz <- (zmin + halfStep) to zmax by step
    } {
      val r = frep.evaluate(mx, my, mz)
      results += r
      if (math.abs(r.value) < step && step > .2) {
        results ++= recursiveEval(frep, mx - halfStep, mx + halfStep, my - halfStep, my + halfStep, mz - halfStep, mz + halfStep, step / 4)
      }
    }
    return results
  }

  def grid(xmin: Double, xmax: Double, ymin: Double, ymax: Double, zmin: Double, zmax: Double, step: Double): Seq[Coordinate] = {
    val halfStep = step/2
    for {
      mx <- (xmin + halfStep) to xmax by step
      my <- (ymin + halfStep) to ymax by step
      mz <- (zmin + halfStep) to zmax by step
    } yield new Coordinate(mx,my,mz)
  }

  def findSurface(fRep: FRep, coordinates: Seq[EPoint], oldStep: Double, newStep: Double): Seq[EPoint] = {
    val halfStep = oldStep / 2
    val threshold = oldStep

    def _refinePoint(e: EPoint): Seq[EPoint] =
      if (math.abs(e.value) > threshold) List()
      //else if (math.abs(e.value) < 0.05) List(e)
      else grid(e.c.x - halfStep, e.c.x + halfStep, e.c.y - halfStep, e.c.y + halfStep, e.c.z - halfStep, e.c.z + halfStep, newStep).map(fRep.evaluate(_))

    coordinates.flatMap(_refinePoint(_)).filter(_.within(.05))
  }

  def iterativeSurfaceEval(frep: FRep, initialStep: Double): ListBuffer[EPoint] = {
    var skipped = 0
    var total = 0
    val results = new scala.collection.mutable.ListBuffer[EPoint]
    val halfStep = initialStep / 2
    val threshold = math.sqrt(3*halfStep*halfStep)
    for {
      mx <- (xmin + halfStep) to xmax by initialStep
      my <- (ymin + halfStep) to ymax by initialStep
      mz <- (zmin + halfStep) to zmax by initialStep
    } {
      val r = frep.evaluate(mx,my,mz)
      total += 1
      if(r.within(.05))
        results += r
      if(math.abs(r.value) < threshold) {
        results.appendAll(evaluate(frep, mx-halfStep, mx+halfStep, my-halfStep, my+halfStep, mz-halfStep, mz+halfStep, initialStep/5).filter(_.within(.05)))
      }
      else {
        skipped += 1
       // println("skipping " + (mx,my,mz))
      }
    }
    println("Skipped " + skipped + " out of " + total)
    return results
  }

  def evaluate(frep: FRep, xmin: Double, xmax: Double, ymin: Double, ymax: Double, zmin: Double, zmax: Double, step: Double): Seq[EPoint] = {
    val results = new ArrayBuffer[EPoint]
    for {
      x <- xmin to xmax by step
      y <- ymin to ymax by step
      z <- zmin to zmax by step
    } {
      val r = frep.evaluate(x,y,z)
      results.append(r)
    }
    return results
  }

}
