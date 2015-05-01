package org.scaml

/**
 * Annotations for `Nodes`. Has two direct subclasses: Attribute and Processor.
 *
 * @param name Name of this annotation
 *
 * @define name annotation
 * @define Name Annotation
 */
sealed abstract class Annotation[T](val name: String) {
  /**
   * Creates a modifier.
   */
  def >(value: T) = new Modifier(this, value)

  //XXX: Casting
  def unapply(modifier: Modifier[_]) =
    if (modifier.annotation == this)
      Some(modifier.value.asInstanceOf[T])
    else
      None

  override def toString = name
}

/**
 * Attributes are passive data for a generator, e.g. FontFamily.
 */
class Attribute[T](name: String) extends Annotation[T](name)

/**
 * A processor can modify annotated nodes.
 */
abstract class Processor[T](name: String) extends Annotation[T](name) {
  def apply(root: Element, node: Element, value: T): Node
}

object Processor {
  def apply(name: String) = new Factory(name)

  class Factory(name: String) {
    /**
     * The processor takes the so far constructed document, the annotated element and the parameter
     * given by the annotation and returns the new node.
     */
    def apply[T](p: (Element, Element, T) => Node) = new Processor[T](name) {
      def apply(root: Element, node: Element, value: T) =
        p(root, node, value)
    }

    /**
     * The annotated element and the parameter given by the annotation and returns the new node.
     */
    def apply[T](p: (Element, T) => Node) = new Processor[T](name) {
      def apply(root: Element, node: Element, value: T) =
        p(node, value)
    }
  }
}

