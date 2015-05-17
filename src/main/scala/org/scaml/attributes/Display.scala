package org.scaml.attributes

import org.scaml.Attribute

private[scaml] trait Display {
  /**
   * Controls how the element will be displayed.
   */
  val Display = new Attribute[DisplayKind]("Display")

  sealed class DisplayKind private[Display] (val name: String) {
    override def toString = name
  }

  /**
   * Marks a element as block, means it got it's own line. Block elements could
   * only place in other block elements.
   */
  val block = new DisplayKind("block")

  /**
   * Marks a in-line element within a block or an other in-line element.
   */
  val inline = new DisplayKind("inline")

  /**
   * Contains all display kinds to describe table.
   *
   * @note table is it self the starting point for a table definition.
   */
  object table extends DisplayKind("table") {
    /**
     * A single table cell.
     */
    val cell = new DisplayKind("table-cell")
    /**
     * A row of a table, should contains multiple [[cell]]s.
     */
    val row = new DisplayKind("table-row")
    /**
     * If a tables breaks the header will be repeated. Together with [[body]] and
     * [[footer]] the only direct children of a table. Good for headlines.
     */
    val header = new DisplayKind("table-header")
    val body = new DisplayKind("table-body")
    val footer = new DisplayKind("table-footer")
  }
}
