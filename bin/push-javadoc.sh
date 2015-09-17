#!/bin/sh

# 必须在项目目录下

rm -rf target/docs
git clone git@github.com:advanced-programs/common-utils.git target/docs -b gh-pages
mvn -Pjavadoc javadoc:javadoc
cd target/docs
git add -A
git commit -m "更新 JavaDocs"
git push origin gh-pages
