package org.scaml.templates

import org.scaml._
import org.scaml.attributes._

trait General extends Builder {

  def text =
    FontFamily > "Times Roman" &
    FontSize > 12.pt &
    TextAlign > "justify"

  def p = text &
    Display > block &
    SpaceAfter > 6.pt

  def headline = text &
    FontFamily > "Verdana" &
    FontSize > 28.pt &
    SpaceBefore > 10.pt &
    SpaceAfter > 6.pt &
    bold &
    TextAlign > "left" &
    Display > block

  def title = headline &
    FontSize > 38.pt &
    SpaceAfter > 10.pt &
    SpaceBefore > 12.pt &
    BreakBefore > page

  def subtitle = headline

  def author =
    headline &
    PreText > "Autor: "

  def chapter = headline &
    BreakBefore > page &
    FontSize > 32.pt

  def section = headline

  def subsection = headline &
    FontSize > 21.pt

  /** Emphasis text */
  def em = text & bold

  def list = {
    val block = text
    val line =
      text &
        SpaceAfter > 0.pt &
        PreText > "• "

    line asMinorOf block
  }
}

