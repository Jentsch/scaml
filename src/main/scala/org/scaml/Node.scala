package org.scaml

/**
 * Base class for document content.
 */
sealed trait Node extends Inlineable {
  protected[scaml] def toText: String

  /**
   * @return the result and the remaining input
   */
  override def consume(input: List[Either[Inlineable, String]]): (Node, List[Either[Inlineable, String]]) =
    (this, input)
}

/**
 * Text elements contains only one string and have no attributes.
 */
final case class Text(text: String) extends Node {
  def toText: String = text
}

/**
 * A group of elements, maybe with additional modifiers.
 */
final case class Element(children: Seq[Node], modifiers: Modifiers = Modifiers.empty) extends Node {
  override def toString: String =
    modifiers.mkString("Element(", ", ", "") + children.mkString(" :", ", ", ")")

  protected[scaml] def toText: String = children.map(_.toText).mkString("")
}
