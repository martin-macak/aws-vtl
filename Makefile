SHELL := /bin/bash
MAKE := make

GRADLE := ./gradlew

SRC = $(shell find src -type f -name '*.java')

all: jar

jar: build/libs/aws-apigateway-velocity-repl.jar

build/libs/aws-apigateway-velocity-repl.jar: $(SRC) build.gradle
	@$(GRADLE) jar --no-daemon

dist: build/libs/aws-apigateway-velocity-repl.jar
	@if [[ ! -f $< ]]; then >&2 echo 'Failed to locate build artifacts. Run make build first'; exit 1; fi
	@mkdir -p dist
	@zip -r dist/jar.zip $<
	@tar -czf dist/jar.tar.gz $<
	@touch $@

test:
	@$(GRADLE) test
	@tests/test_dist.sh

.PHONY: test

clean:
	@rm -rf build
	@rm -rf tmp
	@rm -rf dist
