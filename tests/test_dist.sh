#!/usr/bin/env bash

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

jar_file="${__dir}/../build/libs/aws-apigateway-velocity-repl.jar"

if [[ ! -f "$jar_file" ]]; then
  echo "jar file not found"
  exit 1
fi

tmp=$(mktemp -d)
java -jar "$jar_file" \
  -d "$__dir/../src/test/resources/test_data.json" \
  -t "$__dir/../src/test/resources/test_template.vt" \
  > "$tmp/out"

got=$(cat "$tmp/out")

if [[ "$got" != "bar" ]]; then
  exit 1
fi