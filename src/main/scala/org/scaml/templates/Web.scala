package org.scaml.templates

import org.scaml.attributes._
import org.scaml.{Element, Modifiers, Node}

trait Web extends General {
  /** Paragraph */
  override def p: Modifiers = Tag > "p"

  /**
   * Highest level of all headlines
   */
  override def title: Modifiers = Tag > "h1"

  override def chapter: Modifiers = Tag > "h2"

  override def section: Modifiers = Tag > "h3"

  def header: Modifiers = Tag > "header"

  def footer: Modifiers = Tag > "footer"

  /**
   * Usage:
   * {{{
   *   postForm("/register") | p"""
   *     $section {news letter}
   *     $label E-Mail $textInput $br
   *     $button
   *   """
   * }}}
   *
   * Or inlined
   *
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

  override def em: Modifiers = Tag > "em"

  def strong: Modifiers = Tag > "strong"

  def label(`for`: String) =
    Tag > "label" &
      WebAttribute("for") > `for`

  def sup: Modifiers = Tag > "sup"

  def sub: Modifiers = Tag > "sub"


  def list =
    Tag > "li" asMinorOf Tag > "ul"
}