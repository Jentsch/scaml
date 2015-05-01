package org.scaml.attributes

final class Distance private[Distance] (val millimeter: Double) extends AnyVal with Ordered[Distance] {
  override def toString = millimeter + "mm"

  def centimeter = millimeter * 0.1
  // Based upon http://www.einheitenumrechner.mobi/ehu.php
  def point = millimeter * 2.6591145149

  def *(num: Double) = Distance(millimeter * num)
  def +(that: Distance) = new Distance(this.millimeter + that.millimeter)
  def -(that: Distance) = new Distance(this.millimeter - that.millimeter)
  def compare(that: Distance) = this.millimeter compare that.millimeter
}

object Distance {
  def apply(n: Double) =
    if (n.isNaN || n.isInfinity)
      throw new IllegalArgumentException("Distances should be valid numbers")
    else
      new Distance(n)

  def millimeter(n: Double) = Distance(n)
  def centimeter(n: Double) = Distance(n / 1.mm.centimeter)
  def point(n: Double) = Distance(n / 1.mm.point)

  implicit object Ordering extends math.Ordering[Distance] {
    def compare(d1: Distance, d2: Distance) = d1.millimeter compare d2.millimeter
  }
}

