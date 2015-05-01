package org.scaml

import org.scaml.attributes.{red, TextColor}
import org.specs2._

class InlineFormatTest extends Specification {
  object TestCase extends templates.General {
    val a = p"This is ${TextColor > red} red"
    val b = p"This is ${TextColor > red} red and this not"
    val c = p"This is ${TextColor > red} {red and this also}"
  }

  object textparts {
    def of (e: Element) = e.children.map(_.toText)
  }

  import TestCase._

  def is = s2"""
${"Inline format".title}
  ${textparts of a must be equalTo Seq("This is ", "red")}
  ${textparts of b must be equalTo Seq("This is ", "red", " and this not")}
    without a whitespace before "red"
  ${textparts of c must be equalTo Seq("This is ", "red and this also")}
  """
}
