package org.scaml

import org.scaml.attributes._
import org.specs2._

class HtmlTest extends Specification {
  def DirectAttribute = new WebAttribute[String]("attribute")

  def is = s2"""
  ${"HTML Test".title}
  ${HTML(ml"${DirectAttribute > "1"} word").toString must contain( """attribute="1"""")}
  ${HTML(ml"${Link > "#top"} {top}").toString must contain("<a") and contain("""href="#top"""")}

  ${HTML.nameTranslation(new Attribute[String]("camelCase")) should beEqualTo("camel-case")}
  ${HTML(ml"${new Attribute[String]("camelCase") > "x"} w").toString must contain("""style="camel-case: x"""")}
"""
}
