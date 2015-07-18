package org.scaml

/**
 * Trait for everything that could be used inside an ML-Context. E.g
 *
 * {{{
 *   val colorName = "blue"
 *
 *   ml"The color $blue looks like ${TextColor > blue}{this}"
 * }}}
 *
 * Create implicit conversions for every class the should be usable within a StringContext. String already has an
 * implicit conversion to a [Text] [Node]
 * Note the String could be implicitly converted to text node. Inlineables like [[org.scaml.AttributeValuePair]] or functions
 * affecting the content after it.
 */
trait Inlineable {

  /**
   * @return the result and the remaining input
   */
  def consume(input: List[Either[Inlineable, String]]): (Node, List[Either[Inlineable, String]])
}
