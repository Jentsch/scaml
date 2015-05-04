package org.scaml.templates

import org.scaml.{Element, Modifiers, Node}
import org.scaml.attributes._

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
   *   form | p"""
   *     $section {news letter}
   *     $label E-Mail $input $br
   *     $button
   *   """
   * }}}
   *
   */
  def form: Modifiers = Tag > "form"

  def input: Modifiers = Tag > "input"

  def button: Modifiers = Tag > "button"

  def br: Node = Element(Nil, Tag > "br")

  override def em: Modifiers = Tag > "em"

  def strong: Modifiers = Tag > "strong"

  def label: Modifiers = Tag > "label"

  def sup: Modifiers = Tag > "sup"

  def sub: Modifiers = Tag > "sub"


  def list =
    Tag > "li" asMinorOf Tag > "ul"
}