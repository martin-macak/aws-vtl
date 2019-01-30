package awsApiGateway.mapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.lang.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String escapeJavaScript(String value) {
        return StringEscapeUtils.escapeJavaScript(value);
    }

    public Object parseJson(String json)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, Object.class);
    }

    public String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public String urlDecode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }
    
    public byte[] base64Encode(String value) {
        return Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8));
    }
    
    public byte[] base64Decode(String value) {
        return Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
    }
}