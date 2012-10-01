package org.scex.attributes

trait Breaks {
  sealed class Break(val name: String)
  
  val auto = new Break("auto")
  object page extends Break("page") {
    val odd = new Break("odd-page")
    val even = new Break("even-page")
  }
  val column = new Break("coluomn")
  
  val BreakBefore = new Attribute[Break]
  val BreakAfter  = new Attribute[Break]
}