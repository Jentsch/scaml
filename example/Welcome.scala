package org.scaml.sample

import org.scaml._
import org.scaml.attributes._

object Welcome extends templates.Run {
  title"Scaml"

  subtitle"An innerDSL to describe Documents"

  p"Follow on ${Link > "http://github.com"} GitHub"
}
