package org.scaml

import org.scaml.attributes._

/**
 * Converts ScaML documents into HTML.
 */
object HTML {
  def apply(document: Node): String = {
    val result = new StringBuilder()
    content(htmlPreamble(document), result)
    result.toString()
  }

  private def htmlPreamble(content: Node): Node = ml"""
${Tag > "html"}{
  ${Tag > "head"} {
    ${Tag > "meta" & WebAttribute("http-equiv") > "Content-Type" & WebAttribute("content") > "text/html; charset=utf-8"}
  }
  ${Tag > "body"} $content
}
  """

  private def content(node: Node, result: StringBuilder): Unit = node match {
    case Text(text) =>
      result ++= text.replace("<", "&lt;")
    case Element(children, modifiers) =>

      // Write Tag
      result += '<'
      val tag = if (modifiers isDefinedAt Link) {
        "a"
      } else {
        modifiers.getOrElse(Tag, "span")
      }
      result ++= tag

      // Write Attributes
      val attrs: Map[String, String] = {
        val webModifiers = modifiers.collect { case web if web.attribute.isInstanceOf[WebAttribute[_]] =>
          web.attribute.name -> web.attribute.asInstanceOf[WebAttribute[web.attribute.Value]].stringRepresentation(web.value)
        }

        Map("style" -> style(styleModifiers(modifiers))).filterNot(_._2.isEmpty) ++ webModifiers
      }
      attrs foreach { case (key, value) =>
        result += ' '
        result ++= key
        result ++= "=\""
        result ++= value.replace("\"", "&qout;")
        result += '"'
      }

      if (children.nonEmpty) {
        result += '>'

        children foreach { child =>
          content(child, result)
        }

        result ++= "</"
        result ++= tag
        result += '>'
      } else {
        result ++= "/>"
      }
  }

  private def style(modifiers: Modifier): String =
    modifiers.collect {
      case TextUnderline(true) =>
        "text-decoration: underline"
      case BreakBefore(page) =>
        "page-break-before:always"
      case BreakAfter(page) =>
        "page-break-after:always"
      case modifier =>
        nameTranslation(modifier.attribute) + ": " + modifier.value.toString
    }.mkString("; ")

  protected[scaml] val nameTranslation = Map[Attribute[_], String](
    TextColor -> "color",
    SpaceAfter -> "margin-bottom",
    SpaceBefore -> "margin-top").
    withDefault(genericToCSS)

  private def genericToCSS(attribute: Attribute[_]): String =
    attribute.name.foldLeft("") {
      case ("", first) =>
        first.toLower.toString
      case (r, upper) if upper.isUpper =>
        r + '-' + upper.toLower
      case (r, lower) =>
        r + lower
    }

  private def styleModifiers(modifiers: Modifier) =
    modifiers.filter { modifier =>
      !modifier.attribute.isInstanceOf[WebAttribute[_]] &&
        !isIgnoredAttribute(modifier.attribute)
    }

  private val isIgnoredAttribute: Set[Attribute[_]] = Set(Tag)

}

