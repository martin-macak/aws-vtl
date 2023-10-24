import awsApiGateway.mapper.ApiGatewayMapper;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

public class ApiGatewayMapperTest {
    @Test
    public void testRenderTemplate() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("foo", "bar");

        ApiGatewayMapper mapper = new ApiGatewayMapper(data);
        URL resource = this.getClass().getResource("test_template.vt");
        assert (resource != null);

        String templatePath = resource.getFile();

        Optional<String> got = mapper.renderTemplate(templatePath);
        assert (got.isPresent());
        String val = got.get();
        assert (val.equals("bar"));
    }

    @Test
    public void testRenderTemplate_withStageVariables() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("foo", "bar");

        HashMap<String, Object> stageVariables = new HashMap<>();
        stageVariables.put("stage", "dev");

        ApiGatewayMapper mapper = new ApiGatewayMapper(data, stageVariables);
        URL resource = this.getClass().getResource("test_template2.vt");
        assert (resource != null);

        String templatePath = resource.getFile();

        Optional<String> got = mapper.renderTemplate(templatePath);
        assert (got.isPresent());
        String val = got.get();
        assert (val.equals("dev"));
    }

    @Test
    public void testRenderTemplate_withNullAndInputJson() {
        HashMap<String, Object> data = new HashMap<>();

        HashMap<String, Object> stageVariables = new HashMap<>();

        ApiGatewayMapper mapper = new ApiGatewayMapper(data, stageVariables);
        URL resource = this.getClass().getResource("test_template3.vt");
        assert (resource != null);

        String templatePath = resource.getFile();

        Optional<String> got = mapper.renderTemplate(templatePath);
        assert (got.isPresent());
        String val = got.get();
        assert (val.equals("{\n" +
                "  \"contracts\": null,\n" +
                "  \"nextToken\": null\n" +
                "}\n"));
    }
}
