package org

import scala.language.implicitConversions
import scala.util.DynamicVariable

/**
 * Scaml is a tool to build documents in Scala.
 */
package object scaml {
  private val _stringContext = new DynamicVariable[Option[StringContext]](None)

  def stringContext: Option[StringContext] = _stringContext.value

  def stringContext_=(sc: StringContext): Unit =
    _stringContext.value = Some(sc)

  implicit def stringToText(str: String): Text = Text(str)

  implicit def stringsToTexts(strings: Iterable[String]): Seq[Text] =
    strings.to[Seq].map(Text)
}

