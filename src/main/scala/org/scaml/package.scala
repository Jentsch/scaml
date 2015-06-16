package org

import scala.language.implicitConversions

/**
 * Scaml is a innerDSL to build documents in Scala. To create documents extend the [[org.scaml.templates.Web]] trait.
 */
package object scaml {
  implicit def stringToText(str: String): Text = Text(str)

  implicit def stringsToTexts(strings: Iterable[String]): Seq[Text] =
    strings.to[Seq].map(Text)

  implicit def functionsToCurlyInlinable(function: Node => Node): CurlyInlineable = new CurlyInlineable {
    override def wrap(input: List[Node]): Node = function(Element(input))
  }
}

