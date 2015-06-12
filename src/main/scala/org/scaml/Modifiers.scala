package org.scaml

import scala.collection.mutable.ListBuffer

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
  def attributes: Set[Attribute[_]] = modifiers.map {
    _.attribute
  }.toSet

  override def filter(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filter condition)

  override def filterNot(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filterNot condition)

  /**
   * Binds attributes to a node a append the node to a builder.
   */
  def |(node: Node)(implicit parent: Builder): Element = {
    val result = node add this
    parent register result
    result
  }

  def asMinorOf(that: Modifiers): BatchModifiers =
    BatchModifiers(that, this)

  override def toString() = modifiers.mkString("Modifiers(", ", ", ")")

  /**
   * Used for the string interpolation feature.
   *
   * Example:
   * {{{
   *   def p: Modifiers = FontFamily > "Arial"
   *
   *   p"A $bold link ${link > "wikipedia.org"}{to wikipedia}."
   * }}}
   */
  def apply(params: Inlineable*)(implicit b: Builder): Element = {
    val parts = Builder.stringContext.getOrElse(sys.error("No string context given")).parts

    val content: List[Either[Inlineable, String]] =
      rightLeft(params.toList, parts.toList)

    var remaining: List[Either[Inlineable, String]] = content
    val collected = ListBuffer.empty[Node]
    while (remaining.nonEmpty) {
      remaining match {
        case Right("") +: rem =>
          remaining = rem
        case Right(text) +: rem =>
          collected += text
          remaining = rem
        case Left(inlineable) +: rem =>
          val (result, rem2) = inlineable.consume(rem)
          collected += result
          remaining = rem2
      }
    }
    val result = Element(collected.toList, this)

    b.register(result)
    result.asInstanceOf[Element]
  }

  private def rightLeft[L, R](lefts: List[L], rights: Seq[R]): List[Either[L, R]] =
    Right(rights.head) ::
      lefts.zip(rights.tail).flatMap { case (param, part) => Left(param) :: Right(part) :: Nil }

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
