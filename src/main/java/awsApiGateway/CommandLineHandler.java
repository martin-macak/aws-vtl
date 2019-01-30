package awsApiGateway;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineHandler {

    private Options options = new Options();
    private String[] required = { "t", "d" };
    
    public CommandLineHandler() {
        options = buildOptions();
    }

    private Options buildOptions() {
        options.addOption("t", true, "velocity template file");
        options.addOption("d", true, "json data file");
        return options;
    }

    public Optional<CommandLine> handleArguments(String[] args) {
        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            if (Arrays.stream(required).anyMatch(opt -> cmd.getOptionValue(opt) == null)) {
                printUsage(options, System.err);
                return Optional.empty();
            }

            return Optional.of(cmd);
        } catch (ParseException e) {
            printUsage(options, System.err);
            return Optional.empty();
        }
    }
    
    private void printUsage(Options options, OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printUsage(pw, HelpFormatter.DEFAULT_WIDTH, "vtl", options);
        pw.flush();
    }
}
