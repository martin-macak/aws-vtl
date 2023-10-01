package awsApiGateway.mapper;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ApiGatewayMapper {
    private final VelocityContext context;

    public ApiGatewayMapper(Map<String, Object> data) {
        this.context = new VelocityContext();
        context.put("input", new Input(data));
        context.put("context", new Context());
        context.put("stageVariables", new StageVariables());
        context.put("util", new Util());
    }

    public ApiGatewayMapper(Map<String, Object> data,
                            Map<String, Object> stageVariables) {
        this.context = new VelocityContext();
        context.put("input", new Input(data));
        context.put("context", new Context());
        context.put("stageVariables", stageVariables);
        context.put("util", new Util());
    }

    public Optional<String> renderTemplate(String templateName) {
        String resDir = new File(templateName).getParentFile().getAbsolutePath();
        String tplFile = new File(templateName).getName();

        Properties p = new Properties();
        p.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        p.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, resDir);

        Velocity.init(p);

        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(tplFile, StandardCharsets.UTF_8.name(), context, writer);
        return Optional.of(writer.toString());
    }
}