/**
 * Created by martin on 4/28/15.
 */
package scafrep

object Coordinate {
  implicit def tupleToCoordinate(t: Tuple3[Double, Double, Double]): Coordinate = new Coordinate(t._1, t._2, t._3)
}

case class Coordinate(x: Double, y: Double, z: Double) {

  import math._

  def +(that: Coordinate) = Coordinate(this.x + that.x, this.y + that.y, this.z + that.z)

  def /(s: Double) = Coordinate(this.x / s, this.y / s, this.z / s)

  def *(s: Double) = Coordinate(this.x * s, this.y * s, this.z * s)

  override def toString(): String = x + " " + y + " " + z

  def distanceFrom(that: Coordinate): Double = (that - this).length()

  def -(that: Coordinate) = Coordinate(this.x - that.x, this.y - that.y, this.z - that.z)

  def length(): Double = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2))

  def dotProduct(that: Coordinate): Double = this.x * that.x + this.y * that.y + this.z * that.z
}