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
case class Modifier[T](attribute: Attribute[T], value: T)
  extends Modifiers {

  protected val modifiers = List(this)

  override def toString() = attribute.toString + "> " + value.toString
}

