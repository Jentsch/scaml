package org.scaml

import scala.collection.mutable.ListBuffer

/**
 * Trait for everything that could be used inside a StringContext. E.g
 *
 * {{{
 *   val colorName = "blue"
 *
 *   p"The color $blue looks like ${TextColor > blue}{this}"
 * }}}
 *
 * Create implicit conversions for every class the should be usable within a StringContext. String allready has an
 * implicit conversion to a [Text] [Node]
 * Note the String could be implicitly converted to text node. Inlinesables like [[org.scaml.Modifier]] affecting the
 * content after it.
 */
trait Inlineable {

  /**
   * @return the result and the remaining input
   */
  def consume(input: List[Either[Inlineable, String]]): (Node, List[Either[Inlineable, String]])
}

/**
 * Inlineable that everything in curly braces after it or single 'word'.
 * Words are groups if none whitespaces and none curly braces.
 */
trait CurlyInlineable extends Inlineable {
  def wrap(input: List[Node]): Node

  /**
   * @return the result and the remaining input
   */
  override def consume(input: List[Either[Inlineable, String]]): (Node, List[Either[Inlineable, String]]) = input match {
    case Right(blank) :: Left(child) :: rest if blank forall (_.isWhitespace)=>
      val (childResult, remaining) = child.consume(rest)
      wrap(List(childResult)) -> remaining
    case Right(SingleWord(word, restText)) :: rest =>
      wrap(List(word)) -> (Right(restText) :: rest)
    case empty if empty.isEmpty =>
      wrap(Nil) -> Nil
    case Right(start) :: rest if start.trim.startsWith("{") =>
      val collected = ListBuffer.empty[Node]
      var rem = Right(start.dropWhile(_.isWhitespace).drop(1)) :: rest
      while (rem.headOption.collect{case Right(end) if end.contains('}') => ()}.isEmpty) {
        rem match {
          case Left(child) :: input =>
            val (childResult, rem2) = child.consume(input)
            collected += childResult
            rem = rem2
          case Right(text) :: input =>
            collected += text
            rem = input
        }
      }
      rem.headOption foreach { case Right(withEnd) =>
        val Array(stillWhitin, outSide) = withEnd.split("}", 2)
          collected += stillWhitin
          rem = Right(outSide) :: rem.tail
      }
      wrap(collected.toList) -> rem
  }

  private object SingleWord {
    def unapply(string: String): Option[(String, String)] = {
      val leftTrimmed = string.dropWhile(_.isWhitespace)
      if (leftTrimmed.startsWith("{")) {
        None
      } else {
        Some(leftTrimmed.span(char => !char.isWhitespace && char != '}'))
      }
    }
  }

}

