#! /bin/bash

JAR_NAME=$(ls -t ./build/libs/ | grep -v plain | grep .jar | head -n 1)

echo ">>>>> 🚀 프로젝트 실행- JAR name: $JAR_NAME"

java -jar ./build/libs/$JAR_NAME