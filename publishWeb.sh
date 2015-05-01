#!/bin/bash

set -o errexit -o nounset

WEB=target/web/

rev=$(git rev-parse --short HEAD)

rm -rf "$WEB"
git clone --single-branch --branch "gh-pages" "https://$GH_PAGES@github.com/Jentsch/scaml.git" "$WEB"

sbt web

cd "$WEB"
git add -A .
git commit -m "rebuild pages at $rev" --author="Travis <travis@example.com>"
git push
cd -