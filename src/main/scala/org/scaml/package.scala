package org

import language.implicitConversions

/**
 * Scaml is a tool to build documents in Scala.
 */
package object scaml {
  implicit def strToText(str: String): Text = Text(str)

  implicit def strsToTexts(strs: Seq[String]): Seq[Text] = strs.map(Text)

  private val _stringContext = new util.DynamicVariable[Option[StringContext]](None)
  def stringContext: Option[StringContext] = _stringContext.value
  def stringContext_=(cs: StringContext): Unit = {
    _stringContext.value = Some(cs)
  }
}

