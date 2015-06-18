package org.scaml

/**
 * A set of Modifiers. Is like a Map[Attribute[T], T], but that can't expressed
 * this way.
 */
trait Modifiers extends Iterable[Modifier[_]] with CurlyInlineable {

  protected val modifiers: Seq[Modifier[_]]

  def iterator: Iterator[Modifier[_]] = modifiers.iterator

  def isDefinedAt(attribute: Attribute[_]): Boolean =
    get(attribute).isDefined

  // XXX: Is in O(n), should be O(log n)
  def get[T](attribute: Attribute[T]): Option[T] =
    modifiers.collectFirst { case attribute(t) => t }

  /**
   * Merges both modifiers. If a attribute is defined in `this` and `that`, the result will have the value of `that` for
   * the attribute.
   */
  def &(that: Modifiers): Modifiers =
    this ++ that


  /**
   * Merges both modifiers. If a attribute is defined in `this` and `that`, the result will have the value of `that` for
   * the attribute.
   */
  def ++(that: Modifiers): Modifiers =
    Modifiers(modifiers.filterNot { bind => that.attributes.contains(bind.attribute) } ++ that.modifiers)

  /**
   * Returns all attributes of this modifier.
   */
  def attributes: Set[Attribute[_]] =
    modifiers.map { _.attribute }.toSet

  override def filter(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filter condition)

  override def filterNot(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filterNot condition)

  override def toString() = modifiers.mkString("Modifiers(", ", ", ")")

  override def wrap(input: List[Node]): Element =
    Element(input, this)
}

object Modifiers {
  val empty: Modifiers = Modifiers(List.empty)

  def apply(binds: Seq[Modifier[_]]): Modifiers = binds match {
    case Seq(single) => single
    case _ => new Modifiers with CurlyInlineable {
      val modifiers = binds
    }
  }
}
