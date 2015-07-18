package org.scaml

import org.scaml.WebElements._
import org.scaml.attributes._
import org.specs2._

class HtmlTest extends Specification {
  def DirectAttribute = new WebAttribute[String]("attribute")


  val form = HTML( ml"""
    ${postForm("/post") & WebAttribute("param") > "\""} {
      ${textInput("e-mail")}
      <tag>
    }
    """)

  
  def is = s2"""

  ${form must contain("method=\"post\"")}
  ${form must contain("<input name=\"e-mail\" length=\"10\"/>")}
### Dedicated HTML attributes  
  ${HTML(ml"${DirectAttribute > "1"} word").toString must contain( """attribute="1"""")}
  ${HTML(ml"${Link > "#top"} {top}").toString must contain("<a") and contain("""href="#top"""")}

### Handling of style attributes
  ${HTML.nameTranslation(new Attribute[String]("camelCase")) should beEqualTo("camel-case")}
  ${HTML(ml"${new Attribute[String]("camelCase") > "x"} w").toString must contain("""style="camel-case: x"""")}

### Escaping
  ${form must not contain "<tag>" and contain("&lt;tag")}
  ${form must not contain "param=\"\"\""}
"""
}
