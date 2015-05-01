package org.scaml.attributes

/**
 * Add unary + and - prefixes operators for boolean annotations. See 'Known Subclasses' below for
 * examples.
 *
 * @define name toggle
 * @define Name Toggle
 */
class Toggle(name: String) extends Attribute[Boolean](name) {

  /**
   * Allows to write `+ $Name` instand of `$Name > true`.
   */
  def unary_+ = this > true
  /**
   * Allows to write `- $Name` instand of `$Name > false`.
   */
  def unary_- = this > false
}
