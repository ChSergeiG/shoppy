#!/bin/bash

version=$(sed -n -e '/projectVersion/ s/.*\= *//p' ./gradle.properties)

./gradlew clean jacocoRootReport :backend:bootJar

cd ./backend || exit

java -Djarmode=layertools -jar ./build/libs/backend-*.jar extract

# shellcheck disable=SC2086
docker buildx build --platform linux/amd64 --no-cache -t chsergeig/shoppy-back:$version -t chsergeig/shoppy-back:latest .

rm -rf ./application || true
rm -rf ./dependencies || true
rm -rf ./snapshot-dependencies || true
rm -rf ./spring-boot-loader || true

docker push chsergeig/shoppy-back:$version
docker push chsergeig/shoppy-back:latest
