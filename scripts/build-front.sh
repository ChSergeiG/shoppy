#!/bin/bash

version=$(sed -n -e '/projectVersion/ s/.*\= *//p' ./gradle.properties)

./gradlew clean :frontend:npm_ci :frontend:npm_run_build

cd ./frontend || exit

# shellcheck disable=SC2086
docker buildx build --platform linux/amd64 --no-cache -t chsergeig/shoppy-front:$version -t chsergeig/shoppy-front:latest .

docker push chsergeig/shoppy-front:$version
docker push chsergeig/shoppy-front:latest
