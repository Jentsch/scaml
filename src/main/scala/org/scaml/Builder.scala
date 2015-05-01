package org.scaml

import scala.language.implicitConversions
import scala.collection.mutable.ListBuffer

/**
 * Helper to create new nodes. Use one of its subclasses to create your own document.
 */
@annotation.implicitNotFound(msg = "Use this method inside a Builder.")
trait Builder extends Element {
  val modifiers = Modifiers.empty

  /** So far collected children */
  private val buffer = ListBuffer.empty[Node]
  private var alreadyBuild = false
  lazy val children = {
    alreadyBuild = true
    buffer.toList
  }

  /** Used by children to register them self. */
  protected implicit val self: Builder = this

  /** Adds a node to this Builder */
  private[scaml] def register(n: Node): Unit = {
    assert(!alreadyBuild, "The build phase is already over!")
    buffer.append(n)
  }

  /**
   * Used to allow Modifiers as String Interpolator. Don't do this at home kids.
   */
  implicit protected def toStringContext(sc: StringContext): this.type = {
    stringContext = sc
    this
  }
}

