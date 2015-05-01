package org.scaml.generators

import org.scaml._
import org.scaml.attributes._

import scala.xml.{Elem => XElem, MetaData, NamespaceBinding, Node => XNode, Null, Text => XText, UnprefixedAttribute}

object HTML {
  private val nameSpace = new NamespaceBinding("ii", "ii", null)

  def apply(node: Node): XNode =
    <html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
      </head>
      <body>
        {content(node)}
      </body>
    </html>

  private def content(node: Node): XNode = node match {
    case Text(text) =>
      XText(text)
    case Element(children, modifiers) =>
      val childrenContent = children map content
      val attrs = Map("style" -> style(styleModifiers(modifiers))).filterNot(_._2.isEmpty)

      modifiers.get(Link) match {
        case Some(path) =>
          elem("a", attrs + ("href" -> path), childrenContent)
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

  private def style(atts: Modifiers): String =
    atts.collect {
      case Modifier(TextUnderline, true) =>
        "text-decoration: underline"
      case Modifier(BreakBefore, page) =>
        "page-break-before:always"
      case Modifier(BreakAfter, page) =>
        "page-break-after:always"
      case Modifier(annotation, value) if annotation != Link =>
        nameTranslation(annotation) + ": " + value.toString
    }.mkString("; ")

  private val nameTranslation = Map[Annotation[_], String](
    TextColor -> "color",
    SpaceAfter -> "margin-bottom",
    SpaceBefore -> "margin-top").
    withDefault(genericToCSS)

  private def genericToCSS(annotation: Annotation[_]): String =
    annotation.name.foldLeft("") {
      case (r, upper) if upper.isUpper =>
        r + '-' + upper.toLower
      case (r, lower) =>
        r + lower
    }.tail


  private def styleModifiers(modifiers: Modifiers) =
    modifiers.filterNot { case Modifier(anno, _) => isIgnoredAnnotation(anno) }

  private val isIgnoredAnnotation: Set[Annotation[_]] = Set(Tag)
}

