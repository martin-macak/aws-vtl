package awsApiGateway.mapper;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

public class Input {

    private Map<String, Object> data;
    private JsonPathRunner jsonPathRunner = new JsonPathRunner();
    private ObjectMapper objectMapper = new ObjectMapper();

    public Input(Map<String, Object> data) {
        this.data = data;
    }

    public String body() throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    public String json(String expression) throws JsonProcessingException {
        return objectMapper.writeValueAsString(path(expression));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> params() {
        return (Map<String, Object>) data.get("params");
    }

    public Object params(String key) {
        return params().get(key);
    }

    public Object path(String expression) {
        return jsonPathRunner.read(data, expression);
    }

    private class JsonPathRunner {
        public JsonPathRunner() {
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
        }

        public Object read(Object document, String expression) {
            return JsonPath.read(document, expression);
        }
    }
}
