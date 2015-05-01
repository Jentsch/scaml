package org.scaml

import org.specs2._

class Index extends Specification {
def is = s2"""
  ${"Test Results".title urlIs "index.html"}
### Core
  ${see(new BuilderTest)}
  ${see(new ModifiersTest)}
  ${see(new InlineFormatTest)}
  ${see(new AutoFormatTest)}

### Generators
  ${pending}

### Attributes
  ${see(new attributes.DistanceTest)}
  ${see(new attributes.HyphenTest)}
"""
}

