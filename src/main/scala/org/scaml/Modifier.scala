package org.scaml

/**
 * A tuple of an Attribute and a value, ready to be applied to a node.
 * A single Modifier extends the seq type Modifiers because it allows all
 * operations like Modifiers.
 *
 * {{{
 * val warning = TextColor > red
 * }}}
 *
 * Where `TextColor` is an `Attribute` (of type `Color`) and `red` a predefined color
 * (both are part of `scaml.attributes`.
 *
 * Inside a `Builder` it's now possible to do:
 * {{{
 * text & warning | "Warning message"
 * }}}
 * or even shorter:
 * {{{
 * val warning = text & TextColor > red
 * warning"Warning message"
 * }}}
 *
 */
sealed abstract class Modifier extends Modifiers {
  val attribute: Attribute[_]
  val value: attribute.Value

  protected val modifiers = List(this)

  override def toString() = attribute.toString + "> " + value.toString

  override def equals(other: Any): Boolean = other match {
    case that: Modifier =>
        attribute == that.attribute &&
        value == that.value
    case _ => false
  }

  override def hashCode(): Int =
    attribute.hashCode() * 31 + value.hashCode()
}

object Modifier {
  def apply[V](attr: Attribute[V], v: V) = new Modifier {
    override val attribute: Attribute[V] = attr
    override val value: V = v
  }
}