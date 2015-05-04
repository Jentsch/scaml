package org.scaml.attributes

/**
 * Adds unary + and - prefixes operators for boolean attributes. See 'Known Subclasses' below for
 * examples.
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
