package org.scaml

/**
 * Attributes are data for a generator, e.g. FontFamily.
 *
 * Each attribute should be instantiated only once. The comparison checks for identity.
 */
class Attribute[T](val name: String) {
  type Value = T

  /**
   * Creates a modifier.
   */
  def >(value: T) = Modifier(this, value)

  //XXX: Casting
  def unapply(modifier: Modifier) =
    if (modifier.attribute == this)
      Some(modifier.value.asInstanceOf[T])
    else
      None

  override def toString = name
}


