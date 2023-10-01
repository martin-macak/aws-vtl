# aws-apigateway-velocity-repl

This is a fork of https://github.com/samdengler/aws-apigateway-velocity-local that fixes
some issues with filesystem files and also publishes a fat-jar

## Usage

`java -jar aps-apigateway-velocity-repl.jar -t template.vt -d data.json -s stage_variables.json`

- `t` is mandatory and specifies the template file
- `d` is mandatory and specifies the data file that will be available as input
- `s` is optional and specifies the stage variables file that will be available as stageVariables map