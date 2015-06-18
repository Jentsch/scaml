package org.scaml.attributes

import org.scaml.Attribute

/**
 * Adds unary + and - prefixes operators for boolean attributes. See 'Known Subclasses' below for
 * examples.
 *
 * @define name toggle
 * @define Name Toggle
 */
class Toggle(name: String) extends Attribute[Boolean](name) {

  /**
   * Allows to write `+ $Name` instant of `$Name > true`.
   */
  def unary_+ = this > true
  /**
   * Allows to write `- $Name` instant of `$Name > false`.
   */
  def unary_- = this > false
}
