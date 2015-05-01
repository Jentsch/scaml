package org.scaml.attributes

import org.specs2._
import org.scaml._

class HyphenTest extends Specification { 

  // Makes the tests more readable
  private object hyphen {
    case class of(text: String) {
      def in(lang: Lang) = lang.addHypenTo(text).replace("\u00AD", "-")
    }
  }

def is = s2"""
  ${"Hypen".title}
### German
  ${hyphen of "Weltraum" in de must be equalTo "Welt-raum"}
  ${hyphen of "Wer ist im Weltraum? Die Autobahn!" in de must be equalTo "Wer ist im Welt-raum? Die Auto-bahn!"}
"""
}

