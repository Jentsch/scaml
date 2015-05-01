package org.scaml

/**
 * Used to modify multiple nodes.
 *
 * {{{
 * def list = {
 *   val block = text
 *   val line  =
 *     text &
 *     SpaceAfter > 0 &
 *     PreText > "• "
 *
 *   line asMinorOf block
 * }
 *
 * list ||
 *   "Item 1" |
 *   "Item 2"
 * }}}
 *
 *
 */
class BatchModifiers(val major: Modifiers, val minor: Modifiers) {
  import BatchModifiers._

  def |(node: Node)(implicit parent: Builder): BatchElement = {
    val element = new BatchElement(major, minor)
    parent.register(element)

    element | node
  }

  def |(nodes: Seq[Node])(implicit parent: Builder): Unit = {
    var element = new BatchElement(major, minor)
    parent.register(element)

    // XXX: Side effects
    nodes foreach { element | _ }
  }
}

object BatchModifiers {
  def apply(major: Modifiers, minor: Modifiers) =
    new BatchModifiers(major, minor)

  class BatchElement(
    override val modifiers: Modifiers,
    val minor: Modifiers)
    extends Element with Builder {

    def |(node: Node) = {
      register(node add minor)
      this
    }

    def |(nodes: Iterable[Node]): BatchElement =
      nodes.foldLeft(this)(_ | _)

    def |(texts: Seq[String]): BatchElement =
      this | texts.map(Text)
  }
}
