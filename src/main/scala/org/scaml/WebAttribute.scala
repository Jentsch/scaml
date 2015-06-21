package org.scaml

/**
 * Directly interpreted as parameter name and value by the [[org.scaml.HTML HTML generator]]. Equality base upon the
 * name. If the type `T` requires a specific conversion to be represented as string override the `stringRepresentation`
 * method.
 *
 * @param name used by the HTML generator as key
 * @tparam V required type of the value
 */
protected[scaml] case class WebAttribute[V](override val name: String) extends Attribute[V](name) {
  def stringRepresentation(value: V) =
    value.toString
}
