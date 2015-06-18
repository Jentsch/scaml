package org.scaml

import org.specs2._

//noinspection RedundantBlock
class Index extends Specification {
def is = s2"""
  ${"Test Results".title urlIs "index.html"}
### Core
  ${see(new HtmlTest)}
  ${see(new InlineFormatTest)}
  ${see(new ModifiersTest)}

### Generators
  ${pending}

### Attributes
  ${see(new attributes.DistanceTest)}
"""
}

