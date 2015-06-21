Scaml
====

[![Join the chat at https://gitter.im/Jentsch/scaml](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Jentsch/scaml?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/Jentsch/scaml.svg?branch=master)](https://travis-ci.org/Jentsch/scaml)

An inner DSL in Scala to write documents.

How to use?
-----------

With the import of org.scaml._ a new syntax is available:

```scala
import org.scaml._              // Basic syntax
import org.scaml.WebTemplate._  // Allows HTML constructs

object Main extends App {
  val page = ml"""
    $title Headline
    $p {
      To expand the effect of an modifiers like p over mutliple words use currly braces.
    }
  """

  val html = HTML(page)         // converts the page into HTML (using scala.xml)
  println(html)                 // prints the generated page to stdout
}
```

A running Play application could be found here: https://github.com/Jentsch/scaml-play-example

Dependencies
------------

Add following lines to your `build.sbt`
```sbt
libraryDependencies += "org.scaml" %% "scaml" % "0.3.0.19"
resolvers += "ScaML Bintray Repo" at "https://bintray.com/artifact/download/jentsch/maven/"
```

API
----
The API is hosted at https://jentsch.github.io/scaml/api

