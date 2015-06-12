package org

import scala.language.implicitConversions

/**
 * Scaml is a tool to build documents in Scala.
 */
package object scaml {
  implicit def stringToText(str: String): Text = Text(str)

  implicit def stringsToTexts(strings: Iterable[String]): Seq[Text] =
    strings.to[Seq].map(Text)
}

