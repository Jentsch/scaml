package org.scaml.attributes

/**
 * En- or disables the collapsing of white spaces.
 *
 * Example:
 * {{{
 *   ml"""
 *     ${+WhiteSpaceCollapse} {much        space between words}
 *     this   collapse to singe    spaces
 *   """
 * }}}
 *
 * @define name white space collapse
 * @define Name WhiteSpaceCollapse
 */
object WhiteSpaceCollapse extends Toggle("WhiteSpaceCollapse")
