package org.scaml.attributes

import org.specs2.Specification

class ColorTest extends Specification {
  def is = s2"""
  ${"Color".title}
  ${red.toHex must be equalTo "FF0000"}
  ${blue.toHex must be equalTo "0000FF"}
  ${green.toHex must be equalTo "008000"}
  ${rgb(17, 33, 65).toHex must be equalTo "112141"}
  """
}
