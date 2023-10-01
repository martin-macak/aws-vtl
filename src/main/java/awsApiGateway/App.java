package awsApiGateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.cli.CommandLine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

import awsApiGateway.mapper.ApiGatewayMapper;

public class App {

    private ApiGatewayMapper apiGatewayMapper;

    public App(String dataFile) throws JsonParseException, JsonMappingException, IOException {
        this(dataFile, null);
    }

    @SuppressWarnings("unchecked")
    public App(String dataFile, String stageVariablesFile) throws JsonParseException, JsonMappingException, IOException {
        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        Map<String, Object> data = new ObjectMapper().readValue(new File(dataFile), Map.class);

        if (stageVariablesFile != null) {
            Map<String, Object> stageVariables = new ObjectMapper().readValue(new File(stageVariablesFile), Map.class);
            this.apiGatewayMapper = new ApiGatewayMapper(data, stageVariables);
        } else {
            this.apiGatewayMapper = new ApiGatewayMapper(data);
        }
    }

    private Optional<String> renderTemplate(String templateName) {
        return apiGatewayMapper.renderTemplate(templateName);
    }

    private String prettyPrint(String source) throws IOException {
        return source;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CommandLineHandler handler = new CommandLineHandler();
        Optional<CommandLine> cmd = handler.handleArguments(args);

        if (cmd.isPresent()) {
            String templateName = cmd.get().getOptionValue("t");
            String dataFile = cmd.get().getOptionValue("d");
            String stageVariablesFile = cmd.get().getOptionValue("s");

            App app = new App(dataFile, stageVariablesFile);
            Optional<String> result = app.renderTemplate(templateName);
            System.out.println(app.prettyPrint(result.get()));
        } else {
            System.exit(1);
        }
    }
}
