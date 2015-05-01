package org.scaml.templates

import org.scaml.attributes._

trait Web extends General {
  override def p = Tag > "p"

  override def title = Tag > "h1"

  override def chapter = Tag > "h2"

  override def section = Tag > "h3"

  def form = Tag > "form"

  def input = Tag > "input"

  def button = Tag > "button"
}