package org.scaml.templates

import org.scaml._

import scala.xml.XML

/**
 * A document annotated has a main-method which will create a local HTML-file.
 */
trait Run extends General with App {

  override def main(params: Array[String]) {
    super.main(params)

    XML.save(filename, HTML(this), "UTF-8")

    println("Document saved under " + filename)
  }

  private def filename = name + ".html"

  private def name = this.getClass.getSimpleName.takeWhile(_ != '$')
}
