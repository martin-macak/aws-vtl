#!/usr/bin/env bash

>&2 echo "[TESTING] - Executing test_dist_stage_variables.sh"

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

jar_file="${__dir}/../build/libs/aws-apigateway-velocity-repl.jar"

if [[ ! -f "$jar_file" ]]; then
  echo "jar file not found"
  exit 1
fi

tmp=$(mktemp -d)
java -jar "$jar_file" \
  -d "$__dir/../src/test/resources/test_data.json" \
  -t "$__dir/../src/test/resources/test_template2.vt" \
  -s "$__dir/../src/test/resources/test_stage_variables.json" \
  > "$tmp/out"

got=$(cat "$tmp/out")

if [[ "$got" != "stage" ]]; then
  exit 1
fi

>&2 echo "[  OK   ] - Test test_dist_stage_variables.sh passed"
