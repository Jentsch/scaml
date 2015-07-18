package org.scaml

import java.net.URL

/**
 *  A collection of common attributes.
 *
 *  Example of usage
 *  {{{
 *    val warning: Modifiers =
 *      TextColor > rgb(255, 0, 0) &
 *      BackgroundColor > rgb(255, 255, 0)
 *
 *    ml"""
 *      $warning {$bold{Warning:} something went wrong}
 *    """
 *  }}}
 */
package object attributes
  extends Breaks
  with Color.Predef
  with Display {

  object Link extends WebAttribute[String]("href") {
    def >(url: URL): Modifier =
      this > url.toString
  }

  val TextAlign = new Attribute[String]("TextAlign")

  val TextColor = new Attribute[Color]("TextColor")
  val BackgroundColor = new Attribute[Color]("BackgroundColor")

  /**
   *  Puts the first character of each word in uppercase.
   */
  val capitalize = new TextTransform("capitalize")
  /** Puts all characters of each word in uppercase. */
  val uppercase = new TextTransform("uppercase")
  /** Puts all characters of each word in lowercase */
  val lowercase = new TextTransform("lowercase")

  val TextTransform = new Attribute[TextTransform]("TextTransform")

  val TextUnderline = new Toggle("TextUnderline")
  val TextOverline = new Toggle("TextOverline")
  val TextLineThrought = new Toggle("TextLineThrought")
  /** May not supported by all generators. */
  val TextBlink = new Toggle("TextBlink")

  implicit class RichInt(val int: Double) {
    import Distance._
    def mm = millimeter(int)
    def cm = centimeter(int)
    def pt = point(int)
  }

  val SpaceBefore = new Attribute[Distance]("SpaceBefore")
  val SpaceAfter = new Attribute[Distance]("SpaceAfter")

  /**
   * Weight of the font as specified in TrueType between 100 and 900.
   */
  val FontWeight = new Attribute[Int]("FontWeight")
  val bold = FontWeight > 700

  val FontFamily = new Attribute[String]("FontFamily")
  val FontSize = new Attribute[Distance]("FontSize")

  val LineHeight = new Attribute[Int]("LineHeight")

  /** Only for web documents */
  val Tag = new Attribute[String]("Tag")

  /**
   * En- or disables the collapsing of white spaces.
   *
   * Example:
   * {{{
   *   ml"""
   *     ${+WhiteSpaceCollapse} {much        space between words}
   *     this >    <  white spaces collapse to singe    spaces
   *   """
   * }}}
   */
  val WhiteSpaceCollapse = new Toggle("WhiteSpaceCollapse")

}

package attributes {
  final class TextTransform private[attributes] (val name: String)
}
