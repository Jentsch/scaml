package org.scaml

import org.scaml.attributes._
import org.specs2._

class HtmlTest extends Specification {

  val result = HTML(TestCase).toString()

  def is = s2"""
  ${"HTML Test".title}
  ${result must contain( """attribute="1"""")}
  ${result must contain( """href="#top"""")}
"""


  object TestCase extends templates.Web {
    val Attribute = new WebAttribute[String]("attribute")
    p"${Attribute > "1"} word"

    p"${Link > "#top"} {back to top}"
  }

}
