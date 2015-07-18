package org.scaml

import org.scaml.attributes._

trait WebElements {
  /** Paragraph */
  def p: Modifier = Tag > "p"

  protected def headline: Modifier = Modifier.empty
  /**
   * Highest level of all headlines
   */
  def title: Modifier = headline & Tag > "h1"

  def chapter: Modifier = headline & Tag > "h2"

  def section: Modifier = headline & Tag > "h3"

  def header: Modifier = Tag > "header"

  def footer: Modifier = Tag > "footer"

  /**
   * Usage:
   * {{{
   *   p"""
   *     ${postForm("/register)} {
   *       $section {news letter}
   *       $label E-Mail $textInput $br
   *       $button Submit
   *     }
   * """
   * }}}
   *
   */
  def postForm(action: String): Modifier =
    Tag > "form" &
      WebAttribute("method") > "post" &
      WebAttribute("action") > action

  def textInput(name: String, length: Int = 10): Element =
    Element(Nil,
      Tag > "input" &
        WebAttribute("name") > name &
        WebAttribute("length") > length
    )

  def button: Modifier = Tag > "button"

  def br: Node = Element(Nil, Tag > "br")

  def em: Modifier = Tag > "em"

  def strong: Modifier = Tag > "strong"

  def label(`for`: String) =
    Tag > "label" &
      WebAttribute("for") > `for`

  def sup: Modifier = Tag > "sup"

  def sub: Modifier = Tag > "sub"

  def unorderedList = Tag > "ul"

  def image(url: String) =
    Tag > "img" &
      WebAttribute("src") > url

  def item = Tag > "li"
}

/**
 * Basic
 */
object WebElements extends WebElements