package org.scaml.templates

import org.scaml.Modifiers
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

  def br : Modifiers = Tag > "br"
}