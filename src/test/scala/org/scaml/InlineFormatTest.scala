package org.scaml

import org.scaml.attributes._
import org.specs2._

class InlineFormatTest extends Specification {

  object TestCase extends templates.General {
    override val p = Modifiers.empty
    val Red: Modifier[Color] = TextColor > red
    val Blue: Modifier[Color] = TextColor > blue
    val name = "Bob"

    val a = p"Only text"
    val b = p"Hello $name"
    val c = p"This is $p nested"
    val d = p"This is $Red red and this not"
    val e = p"This is $Red {red and this also}"
    val f = p"This is $Red { red and this is $Blue {blue}}"
    val g = p"This is $Red { red and this is $name}"

    val h = p"$p{This is deeply $p nested}"
  }

  object textParts {
    def of(e: Element) = e.children.map(_.toText)
  }

  import TestCase._

  def is = s2"""
${"Inline format".title}
  ${a must be equalTo Element(Seq("Only text"))}
  ${b must be equalTo Element(Seq("Hello ", name))}
  ${c must be equalTo Element(Seq("This is ", Element(Seq("nested"))))}
  ${textParts of d must be equalTo Seq("This is ", "red", " and this not")}
    without a whitespace before "red"
  ${textParts of e must be equalTo Seq("This is ", "red and this also")}
  ${f must be equalTo Element(Seq("This is ", Element(Seq(" red and this is ", Element(Seq("blue"), Blue), ""), Red)))}
  ${g must be equalTo Element(Seq("This is ", Element(Seq(" red and this is ", "Bob", ""), Red)))}
  ${h must be equalTo Element(Seq(Element(Seq("This is deeply " , Element(Seq("nested")), ""))))}
  """


}
