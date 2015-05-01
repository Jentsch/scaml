package org.scaml.attributes

sealed trait Lang {

  import Lang._

  def hypenOf(word: String): String = hyphen(word)

  /**
   * Adds hypens to all words (groups of letters), leaves non letters untouched.
   */
  def addHypenTo(text: String): String = {
    def helper(text: String, accu: StringBuilder): StringBuilder = {
      val (nonWord, rest) = text span (!_.isLetter)
      val (word, rest2) = rest span (_.isLetter)

      accu.append(nonWord)
      accu.append(hypenOf(word))

      if (rest2.isEmpty)
        accu
      else
        helper(rest2, accu)
    }

    helper(text, new StringBuilder).toString()
  }

  private val hyphen: Map[String, String] = {
    val entries = io.Source.fromInputStream(getClass.getResourceAsStream(toString + ".dic")).getLines()

    entries.
      map(_.replace("-", "\u00AD")).
      flatMap { word =>
      if (word contains '(')
        Seq(word takeWhile (_ != '('), word filterNot Bracket)
      else
        Seq(word)
    }.
      toTraversable.
      groupBy(_.replace("\u00AD", "")).
      mapValues(_.head).
      withDefault(identity)
  }
}

private object Lang {
  val Bracket = Set('(', ')')
}

case object de extends Lang

case object en extends Lang

