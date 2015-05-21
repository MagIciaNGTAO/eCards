#!/bin/bash

base_dir=$(dirname $0)
app_jar=eCards.jar

# Which java to use
if [ -z "$JAVA_HOME" ]; then
    if type -p java; then
        JAVA="java"
    else
        echo "JAVA_HOME not defined and 'java' was not found on the system PATH"
        echo "Please insure JAVA_HOME is set and that the java executable is in your classpath."
        exit
    fi
else
  JAVA="$JAVA_HOME/bin/java"
fi

if [[ "$JAVA" ]]; then
    VERSION=$("$JAVA" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    if [[ "$VERSION" < "1.8" ]]; then
        echo "Found java version $VERSION. Java version 1.8 required."
        exit
    fi
fi

while getopts ":d" opt; do
  case $opt in
    a)
        DAEMON=true
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if [ "$DAEMON" = true ] ; then
    nohup $JAVA -jar $app_jar
else
    $JAVA -jar $app_jar
fi



