package org.scaml.attributes

import org.scaml.Attribute



final class TextTransform private[attributes] (val name: String)
object TextTransform extends Attribute[TextTransform]("TextTransform")
