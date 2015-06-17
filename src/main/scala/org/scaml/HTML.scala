package org.scaml

import org.scaml.attributes._

import scala.xml.{Elem => XElem, MetaData, NamespaceBinding, Node => XNode, Null, Text => XText, UnprefixedAttribute}

object HTML {
  private val nameSpace: NamespaceBinding = scala.xml.TopScope

  def apply(document: Node): XNode = {
    def body(node: XNode): XNode = node match {
      case XElem(_, _, Null, _, child: XElem) =>
        body(child)
      case XElem(prefix, _, attributes, scope, children) =>
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
        val styleMods = modifiers.filterNot(_.attribute.isInstanceOf[WebAttribute[_]])
        val webModifiers = modifiers.collect { case web if web.attribute.isInstanceOf[WebAttribute[_]] =>
          val Modifier(attribute: WebAttribute[_], value) = web
          attribute.name -> attribute.stringRepresentation(value.asInstanceOf[attribute.Value])
        }
        Map("style" -> style(styleModifiers(styleMods))).filterNot(_._2.isEmpty) ++ webModifiers
      }

      modifiers.get(Link) match {
        case Some(path) =>
          elem("a", attrs, childrenContent)
        case None =>
          val tag = modifiers.get(Tag).getOrElse("span")
          elem(tag, attrs, childrenContent)
      }
  }

  private def elem(name: String, attributes: Map[String, String], children: Seq[XNode]) = {
    val meta: MetaData = attributes.foldLeft(Null: MetaData) {
      case (coll, (key, value)) => new UnprefixedAttribute(key, value, coll)
    }

    XElem(null, name, meta, nameSpace, true, children: _*)
  }

  private def style(modifiers: Modifiers): String =
    modifiers.collect {
      case Modifier(TextUnderline, true) =>
        "text-decoration: underline"
      case Modifier(BreakBefore, page) =>
        "page-break-before:always"
      case Modifier(BreakAfter, page) =>
        "page-break-after:always"
      case Modifier(attribute, value) if attribute != Link =>
        nameTranslation(attribute) + ": " + value.toString
    }.mkString("; ")

  private val nameTranslation = Map[Attribute[_], String](
    TextColor -> "color",
    SpaceAfter -> "margin-bottom",
    SpaceBefore -> "margin-top").
    withDefault(genericToCSS)

  private def genericToCSS(attribute: Attribute[_]): String =
    attribute.name.foldLeft("") {
      case (r, upper) if upper.isUpper =>
        r + '-' + upper.toLower
      case (r, lower) =>
        r + lower
    }.tail


  private def styleModifiers(modifiers: Modifiers) =
    modifiers.filterNot { case Modifier(attribute, _) => isIgnoredAttribute(attribute) }

  private val isIgnoredAttribute: Set[Attribute[_]] = Set(Tag)
}

