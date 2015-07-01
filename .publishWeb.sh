#!/bin/bash

set -o errexit -o nounset

WEB=target/web/

rev=$(git rev-parse --short HEAD)

rm -rf "$WEB"
git clone --single-branch --branch "gh-pages" "https://$GH_PAGES@github.com/Jentsch/scaml.git" "$WEB"
rm -rf "$WEB/"*

sbt web

cd "$WEB"
git add -A .
git config --local user.name "Travis"
git config --local user.email "Travis <travis@example.com>"
git config --local push.default matching
git commit -m "rebuild pages at $rev"
git push -q
cd -
