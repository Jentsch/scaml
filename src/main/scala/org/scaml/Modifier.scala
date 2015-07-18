package org.scaml

/**
 * A set of Modifiers. Is like a Map[Attribute[T], T], but that can't expressed this way.
 */
sealed trait Modifier extends Iterable[AttributeValuePair] with CurlyInlineable {

  protected val modifiers: Seq[AttributeValuePair]

  def iterator: Iterator[AttributeValuePair] = modifiers.iterator

  def isDefinedAt(attribute: Attribute[_]): Boolean =
    get(attribute).isDefined

  // XXX: Is in O(n), should be O(log n)
  def get[T](attribute: Attribute[T]): Option[T] =
    modifiers.collectFirst { case attribute(t) => t }

  def getOrElse[T](attribute: Attribute[T], default: => T) =
    get(attribute).getOrElse(default)

  /**
   * Merges both modifiers. If a attribute is defined in `this` and `that`, the result will have the value of `that` for
   * the attribute.
   */
  def &(that: Modifier): Modifier =
    this ++ that


  /**
   * Merges both modifiers. If a attribute is defined in `this` and `that`, the result will have the value of `that` for
   * the attribute.
   */
  def ++(that: Modifier): Modifier =
    Modifier(modifiers.filterNot { bind => that.attributes.contains(bind.attribute) } ++ that.modifiers)

  /**
   * Returns all attributes of this modifier.
   */
  def attributes: Set[Attribute[_]] =
    modifiers.map { _.attribute }.toSet

  override def filter(condition: AttributeValuePair => Boolean): Modifier =
    Modifier(modifiers filter condition)

  override def filterNot(condition: AttributeValuePair => Boolean): Modifier =
    Modifier(modifiers filterNot condition)

  override def toString() = modifiers.mkString("Modifiers(", ", ", ")")

  override def wrap(input: List[Node]): Element =
    Element(input, this)
}

object Modifier {
  val empty: Modifier = Modifier(List.empty)

  private[Modifier] def apply(binds: Seq[AttributeValuePair]): Modifier = binds match {
    case Seq(single) => single
    case _ => new Modifier {
      val modifiers = binds
    }
  }
}

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
sealed abstract class AttributeValuePair extends Modifier {
  val attribute: Attribute[_]
  val value: attribute.Value

  protected val modifiers = List(this)

  override def toString() = attribute.toString + "> " + value.toString

  override def equals(other: Any): Boolean = other match {
    case that: AttributeValuePair =>
      attribute == that.attribute &&
        value == that.value
    case _ => false
  }

  override def hashCode(): Int =
    attribute.hashCode() * 31 + value.hashCode()
}

object AttributeValuePair {
  def apply[V](attr: Attribute[V], v: V) = new AttributeValuePair {
    override val attribute: Attribute[V] = attr
    override val value: V = v
  }
}