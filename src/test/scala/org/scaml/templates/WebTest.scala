package org.scaml.templates

import org.scaml.HTML
import org.specs2._

class WebTest extends Specification {
  def is = s2"""
  ${"WebTest".title}
  ${result must contain("method=\"post\"")}
  ${result must contain("<input length=\"10\" name=\"e-mail\"/>")}
"""

  val result = HTML(TestCase).toString()

  object TestCase extends Web {
    p""" ${postForm("/post")} {
      ${textInput("e-mail")}
    }"""
  }
}
