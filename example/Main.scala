package org.scaml.sample

import org.scaml._
import org.scaml.attributes._
import org.scaml.templates._

object Main extends App {
  private val examples = Map(
    "HTML" -> HTML,
    "Welcome" -> Welcome)

  args.headOption getOrElse ("HTML") match {
    case "all" =>
      examples.values foreach (_.main(args drop 1))
    case name =>
      examples get name foreach (_.main(args drop 1))
  }
}
