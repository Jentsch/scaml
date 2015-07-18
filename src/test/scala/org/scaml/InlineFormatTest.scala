package org.scaml

import org.scaml.attributes._
import org.specs2._

class InlineFormatTest extends Specification {

  val q = Modifier.empty
  val Red = TextColor > red
  val Blue = TextColor > blue

  val name = "Bob"
  val note = (node: Node) => ml">$node<"

  val a = ml"Only text"
  val b = ml"Hello $name"
  val c = ml"This is $q nested"
  val d = ml"This is $Red red and this not"
  val e = ml"This is $Red {red and this also}"
  val f = ml"This is $Red { red and this is $Blue {blue}}"
  val g = ml"This is $Red { red and this is $name}"

  val h = ml"$q{This is deeply $q nested}"
  val i = ml"Some text $note {some other text}"
  val j = ml"Some text $q $note {some other text}"
  val k = ml"Some $q"

  def openBraces = ml"$q { left open"

  object textParts {
    def of(e: Element) = e.children.map(_.toText)
  }

  def is = s2"""
  ${a must be equalTo Element(Seq("Only text"))}
  ${b must be equalTo Element(Seq("Hello ", name))}
  ${c must be equalTo Element(Seq("This is ", Element(Seq("nested"))))}
  ${textParts of d must be equalTo Seq("This is ", "red", " and this not")}
    without a whitespace before "red"
  ${textParts of e must be equalTo Seq("This is ", "red and this also")}
  ${f must be equalTo Element(Seq("This is ", Element(Seq(" red and this is ", Element(Seq("blue"), Blue), ""), Red)))}
  ${g must be equalTo Element(Seq("This is ", Element(Seq(" red and this is ", "Bob", ""), Red)))}
  ${h must be equalTo Element(Seq(Element(Seq("This is deeply " , Element(Seq("nested")), ""))))}
  ${i must be equalTo Element(Seq("Some text ", Element(Seq(">", Element(Seq("some other text")), "<"))))}
  ${j must be equalTo Element(Seq("Some text ", Element(Seq(Element(Seq(">", Element(Seq("some other text")), "<"))))))}
  ${k must be equalTo Element(Seq("Some ", Element(Seq(""))))}
  ${ml"$q { left open" should throwAn[RuntimeException](".*missing.*")}
  ${ml"${List(1, 2)}".toText must be equalTo "12"}
  """


}
