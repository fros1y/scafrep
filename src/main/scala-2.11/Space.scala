/**
 * Created by martin on 4/28/15.
 */
package scafrep
import FReps._
import BoundaryPositions._

class Space(val xmin: Double = -50, val xmax: Double = 50,
            val ymin: Double = -50, val ymax: Double = 50,
            val zmin: Double = -50, val zmax: Double = 50,
            val step: Double = .1) extends Iterable[Coordinate] {

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
    evaluation_points.filter(fRep.evaluate(_).within(0.05))
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
}
