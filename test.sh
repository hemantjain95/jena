#!/bin/bash
# run Jena tests; try and guess path separator.

S=":"
if [ "$OSTYPE" == "cygwin" ]; then S=";"; fi

LIBS="$(cat<<EOF
antlr.jar
commons-logging.jar
concurrent.jar
icu4j.jar
jakarta-oro-2.0.5.jar
jena.jar
jenatest.jar
junit.jar
log4j-1.2.7.jar
xercesImpl.jar
xml-apis.jar
EOF
)"

CP=""
for jar in $LIBS
do
  jar="lib/${jar}"
  [ -e "$jar" ] || echo "No such jar: $jar" 1>&2

  if [ "$CP" == "" ]
  then
      CP="${jar}"
  else
      CP="$CP${S}${jar}"
      fi
  done

##echo $CP

java -classpath "$CP"  junit.textui.TestRunner ${1:-com.hp.hpl.jena.test.TestPackage}
