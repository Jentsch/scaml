package org.scaml

import org.scaml.attributes._

import scala.xml.{Elem => XElem, MetaData, Node => XNode, Null, Text => XText, TopScope, UnprefixedAttribute}

/**
 * Converts ScaML documents into HTML.
 */
object HTML {
  def apply(document: Node): XNode = {
    def body(node: XNode): XNode = node match {
      case XElem(_, _, Null, _, child: XElem) =>
        body(child)
      case XElem(prefix, tag, attributes, scope, children) if noSemantic(tag) =>
        XElem(prefix, "body", attributes, scope, true, children)
      case b => elem("body", Map.empty, Seq(b))
    }

    <html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
      </head>
      {body(content(document))}
    </html>
  }

  private def content(node: Node): XNode = node match {
    case Text(text) =>
      XText(text)
    case Element(children, modifiers) =>
      val childrenContent = children map content
      val attrs: Map[String, String] = {
        val webModifiers = modifiers.collect { case web if web.attribute.isInstanceOf[WebAttribute[_]] =>
          web.attribute.name -> web.attribute.asInstanceOf[WebAttribute[web.attribute.Value]].stringRepresentation(web.value)
        }

        Map("style" -> style(styleModifiers(modifiers))).filterNot(_._2.isEmpty) ++ webModifiers
      }

      val tag = if (modifiers isDefinedAt Link) {
        "a"
      } else {
        modifiers.getOrElse(Tag, "span")
      }
      elem(tag, attrs, childrenContent)
  }

  private def elem(name: String, attributes: Map[String, String], children: Seq[XNode]) = {
    val meta: MetaData = attributes.foldLeft(Null: MetaData) {
      case (coll, (key, value)) => new UnprefixedAttribute(key, value, coll)
    }

    XElem(null, name, meta, TopScope, true, children: _*)
  }

  private def style(modifiers: Modifiers): String =
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

  private def styleModifiers(modifiers: Modifiers) =
    modifiers.filter { modifier =>
      !modifier.attribute.isInstanceOf[WebAttribute[_]] &&
        !isIgnoredAttribute(modifier.attribute)
    }

  private val isIgnoredAttribute: Set[Attribute[_]] = Set(Tag)

  private val noSemantic = Set("div", "span")

}

