package org.scaml

/**
 * A set of Modifiers. Is like a Map[Annotation[T], T], but that can't expressed
 * this way.
 */
trait Modifiers extends Iterable[Modifier[_]] with Inlineable {

  protected val modifiers: Seq[Modifier[_]]

  def iterator: Iterator[Modifier[_]] = modifiers.iterator

  // XXX: Is in O(n), should be O(log n)
  def get[T](annotation: Annotation[T]): Option[T] =
    modifiers.collectFirst { case annotation(t) => t }

  def isDefinedAt(annotation: Annotation[_]): Boolean =
    get(annotation).isDefined

  def &(that: Modifiers): Modifiers =
    this ++ that

  override def filter(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filter condition)

  override def filterNot(condition: Modifier[_] => Boolean): Modifiers =
    Modifiers(modifiers filterNot condition)

  def ++(that: Modifiers): Modifiers =
    Modifiers(modifiers.filterNot { bind => that.annotations.contains(bind.annotation) } ++ that.modifiers)

  /**
   * Binds attributes to a node a append the node to a builder.
   */
  def |(node: Node)(implicit parent: Builder): Element = {
    val result = node add this
    parent register result
    result
  }

  def asMinorOf(that: Modifiers): BatchModifiers =
    BatchModifiers(that, this)

  /**
   * Returns all annotations of this modifier.
   */
  def annotations: Set[Annotation[_]] = modifiers.map { _.annotation }.toSet

  override def toString() = modifiers.mkString("Modifiers(", ", ", ")")

  /**
   * Used for the string interpolation feature.
   *
   * Example:
   * {{{
   *   def p: Modifiers = FontFamily > "Arial"
   *
   *   p"A $bold link ${link > "wikipedia.org"}{to wikipedia}."
   * }}}
   */
  def apply(params: Inlineable*)(implicit b: Builder): Element = {
    val sc = stringContext getOrElse sys.error("No StringContext given")
    val head: Text = sc.parts.headOption getOrElse sys.error("Empty StringContext given")

    val tail: Seq[Node] = params zip sc.parts.tail flatMap {
      // finde modifers with append a append group
      case (m: Modifiers, text) if text.dropWhile(_.isWhitespace).headOption == Some('{') &&
        text.contains('}') =>
        val formated =
          text.dropWhile(_.isWhitespace).tail.takeWhile(_ != '}')
        val unaffected =
          text.dropWhile(_ != '}').tail
        if (unaffected.isEmpty)
          Seq(Text(formated) add m)
        else
          Seq(Text(formated) add m, Text(unaffected))
      case (m: Modifiers, text) =>
        val (formated, unaffected) =
          text.dropWhile(_.isWhitespace).span(!_.isWhitespace)
        if (unaffected.isEmpty)
          Seq(Text(formated) add m)
        else
          Seq(Text(formated) add m, Text(unaffected))
      case (n: Node, text) =>
        Seq(n, Text(text))
      case (any, text) =>
        Seq(Text(any.toString), Text(text))
    }

    val list = (head +: tail).foldRight[List[Node]](Nil) {
      case (Text(""), ls) => ls
      case (Text(t1), Text(t2) :: ls) => Text(t1 + t2) :: ls
      case (h, ls) => h :: ls
    }

    this | Element(list)
  }
}

object Modifiers {
  def apply(binds: Seq[Modifier[_]]): Modifiers = binds match {
    case Seq(single) => single
    case _ => new Modifiers {
      val modifiers = binds
    }
  }
  
  def unapply(modifiers: Modifiers): Option[(Modifier[_], Modifiers)] = modifiers.modifiers match {
    case first +: rest =>
      Some(first -> Modifiers(rest))
    case _ =>
      None
  }

  val empty: Modifiers = Modifiers(List.empty)
}
