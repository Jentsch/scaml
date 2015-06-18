package org

import org.scaml.WebElements

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

/**
 * Scaml is a innerDSL to build documents in Scala. To create documents extend the [[WebElements]] trait.
 */
package object scaml {


  /**
   * Used for the string interpolation feature.
   *
   * Example:
   * {{{
   *   ml"A $bold link ${link > "wikipedia.org"}{to wikipedia}."
   * }}}
   */
  implicit class ML(sc: StringContext) {
    def ml(params: Inlineable*): Element = {
      val parts = sc.parts

      val content: List[Either[Inlineable, String]] =
        rightLeft(params.toList, parts.toList)

      var remaining: List[Either[Inlineable, String]] = content
      val collected = ListBuffer.empty[Node]
      while (remaining.nonEmpty) {
        remaining match {
          case Right("") +: rem =>
            remaining = rem
          case Right(text) +: rem =>
            collected += text
            remaining = rem
          case Left(inlineable) +: rem =>
            val (result, rem2) = inlineable.consume(rem)
            collected += result
            remaining = rem2
        }
      }

      Element(collected.toList)
    }


    private def rightLeft[L, R](lefts: List[L], rights: Seq[R]): List[Either[L, R]] =
      Right(rights.head) ::
        lefts.zip(rights.tail).flatMap { case (param, part) => Left(param) :: Right(part) :: Nil }
  }

  implicit def stringToText(str: String): Text = Text(str)

  implicit def intToText(int: Int): Text = Text(int.toString)

  implicit def stringsToTexts(strings: Iterable[String]): Seq[Text] =
    strings.to[Seq].map(Text)

  implicit def functionsToCurlyInlinable(function: Node => Node): CurlyInlineable = new CurlyInlineable {
    override def wrap(input: List[Node]): Node = function(Element(input))
  }

  implicit def groupTraversable[C](traversable: Traversable[C])(implicit convertElements: C => Node): Element =
    Element(traversable.map(convertElements).to[Seq])
}

