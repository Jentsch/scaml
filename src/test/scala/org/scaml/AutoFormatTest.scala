package org.scaml

import org.scaml.attributes.TextUnderline
import org.specs2._

class AutoFormatTest extends Specification {

  // The result of this mini document will be checked below
  object TestCase extends templates.General {
    case class User(name: String)
    implicit def userRepresentation(user: User): Element =
      user.name add +TextUnderline

    val greeting = "Hello"
    val user = User("Bob")

    val result = p"$greeting $user"
  }
  /** Mini innerDSL to write English like tests */
  // mini innerDSL to write English like tests
  private object textParts {
    def of(e: Element) = e.children.map(_.toText)
  }

  import TestCase._

  def is = s2"""
${"Auto format".title}
  ${textParts of result must be equalTo Seq("Hello ", "Bob")}
  ${result.children(1).asInstanceOf[Element].modifiers.get(TextUnderline) must be equalTo Some(true)}
  """
}
