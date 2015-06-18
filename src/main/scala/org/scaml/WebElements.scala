package org.scaml

import org.scaml.attributes._

trait WebElements {
  /** Paragraph */
  def p: Modifiers = Tag > "p"

  def headline: Modifiers = Modifiers.empty
  /**
   * Highest level of all headlines
   */
  def title: Modifiers = headline & Tag > "h1"

  def chapter: Modifiers = headline & Tag > "h2"

  def section: Modifiers = headline & Tag > "h3"

  def header: Modifiers = Tag > "header"

  def footer: Modifiers = Tag > "footer"

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
  def postForm(action: String): Modifiers =
    Tag > "form" &
      WebAttribute("method") > "post" &
      WebAttribute("action") > action

  def textInput(name: String, length: Int = 10): Element =
    Element(Nil,
      Tag > "input" &
        WebAttribute("name") > name &
        WebAttribute("length") > length
    )

  def button: Modifiers = Tag > "button"

  def br: Node = Element(Nil, Tag > "br")

  def em: Modifiers = Tag > "em"

  def strong: Modifiers = Tag > "strong"

  def label(`for`: String) =
    Tag > "label" &
      WebAttribute("for") > `for`

  def sup: Modifiers = Tag > "sup"

  def sub: Modifiers = Tag > "sub"

  def unorderedList = Tag > "ul"

  def item = Tag > "li"
}

/**
 * Basic
 */
object WebElements extends WebElements