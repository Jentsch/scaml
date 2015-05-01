package org.scaml.templates

import org.scaml._
import attributes._

trait Presentation extends General {

  override def text =
    super.text &
      FontFamily > "Verdana"

  override def headline =
    super.headline &
      BreakBefore > page &
      TextAlign > "center"
}
