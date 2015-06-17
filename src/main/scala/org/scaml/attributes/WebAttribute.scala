package org.scaml.attributes

import org.scaml.Attribute

/**
 * Directly interpreted as parameter name and value by the [[org.scaml.HTML HTML generator]]. Equality base upon the
 * name. If the type `T` requires a specific conversion to be represented as string override the `stringRepresentation`
 * method.
 *
 * @param name used by the HTML generator as key
 * @tparam V required type of the value
 */
class WebAttribute[V](name: String) extends Attribute[V](name) {
  type Value = V

  def stringRepresentation(value: V) =
    value.toString

  override def equals(obj: Any): Boolean = obj match {
    case that: WebAttribute[_] =>
      this.name == that.name
    case _ =>
      false
  }

  override def hashCode(): Int = name.hashCode * 47
}

object WebAttribute {
  def apply[V](name: String) =
    new WebAttribute[V](name)
}