package awsApiGateway.mapper;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class ApiGatewayMapper {

    static {
        Velocity.init();
    }

    private VelocityContext context;

    public ApiGatewayMapper(Map<String, Object> data) {
        this.context = new VelocityContext();
        context.put("input", new Input(data));
        context.put("context", new Context());
        context.put("stageVariables", new StageVariables());
        context.put("util", new Util());
    }

    public Optional<String> renderTemplate(String templateName) {
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(templateName, StandardCharsets.UTF_8.name(), context, writer);
        return Optional.of(writer.toString());
    }
}
