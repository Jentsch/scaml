package org.scaml.templates

import org.scaml._

/**
 * A document annotated with this trait will be runnable.
 */
trait Run extends General with App {

  override def main(params: Array[String]) {
    super.main(params)

    scala.xml.XML.save(filename, generators HTML this, "UTF-8")

    println("Document saved under " + filename)
  }

  private def filename = name + ".html"

  private def name = this.getClass.getSimpleName.takeWhile(_ != '$')
}
