/**
 * Created by martin on 4/28/15.
 */
package scafrep
import BoundaryPositions._

case class Geometry(position: BoundaryPositions) {
  def ||(in: => Geometry) = {
    lazy val that = in
    if (this == Geometry(INSIDE) || that == Geometry(INSIDE)) {
      Geometry(INSIDE)
    } else if (this == Geometry(SURFACE) || that == Geometry(SURFACE)) {
      Geometry(SURFACE)
    } else {
      Geometry(OUTSIDE)
    }
  }

  def &&(in: => Geometry) = {
    lazy val that = in
    if (this == Geometry(OUTSIDE) || that == Geometry(OUTSIDE)) {
      Geometry(OUTSIDE)
    } else if (this == Geometry(SURFACE) || that == Geometry(SURFACE)) {
      Geometry(SURFACE)
    } else {
      Geometry(INSIDE)
    }
  }

  def -(in: => Geometry) = {
    lazy val that = in
    if (this == Geometry(OUTSIDE) || that == Geometry(INSIDE)) Geometry(OUTSIDE)
    else if (this == Geometry(INSIDE) && that == Geometry(SURFACE)) Geometry(SURFACE)
    else this
  }

  override def toString(): String = this.position match {
    case OUTSIDE => "Outside"
    case INSIDE => "Inside"
    case SURFACE => "Surface"
  }
}