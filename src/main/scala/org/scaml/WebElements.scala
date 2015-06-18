package org.scaml

import org.scaml.attributes._

trait WebElements {
  /** Paragraph */
  val p: Modifiers = Tag > "p"

  protected val headline: Modifiers = Modifiers.empty
  
  /**
   * Highest level of all headlines
   */
  def title: Modifiers = headline & Tag > "h1"

  def chapter: Modifiers = headline & Tag > "h2"

  def section: Modifiers = headline & Tag > "h3"

  val header: Modifiers = Tag > "header"

  val footer: Modifiers = Tag > "footer"

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

  val br: Node = Element(Nil, Tag > "br")

  val em: Modifiers = Tag > "em"

  val strong: Modifiers = Tag > "strong"

  def label(`for`: String) =
    Tag > "label" &
      WebAttribute("for") > `for`

  val sup: Modifiers = Tag > "sup"

  val sub: Modifiers = Tag > "sub"

  val unorderedList = Tag > "ul"

  def image(url: String) =
    Tag > "img" &
      WebAttribute("src") > url

  val item = Tag > "li"
}

/**
 * Basic
 */
object WebElements extends WebElements
