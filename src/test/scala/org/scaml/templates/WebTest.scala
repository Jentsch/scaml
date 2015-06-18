package org.scaml.templates

import org.scaml.WebElements._
import org.scaml._
import org.specs2._

class WebTest extends Specification {
  def is = s2"""
  ${"WebTest".title}
  ${result must contain("method=\"post\"")}
  ${result must contain("<input length=\"10\" name=\"e-mail\"/>")}
"""

  val result = HTML(testCase).toString()

  def testCase =
    ml""" ${postForm("/post")} {
      ${textInput("e-mail")}
    }"""

}
