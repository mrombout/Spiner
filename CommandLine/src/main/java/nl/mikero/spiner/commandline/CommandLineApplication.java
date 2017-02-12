package nl.mikero.spiner.commandline;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.commandline.command.*;

/**
 * Provides a commandline interface to the Spiner transformation features.
 * <p>
 * Basic commandline usage:
 * </p>
 * <pre>
 * {@code
 *  usage: application.jar [-help | -version] | [[-f <file>] [-o <file>]]
 *  -f,--file <file>     location of input HTML file
 *  -help                display this help and exit
 *  -o,--output <file>   location of output EPUB file
 *  -version             output version information and exit
 * }
 * </pre>
 *
 * @author Mike Rombout
 */
public class CommandLineApplication {
    private static final String CMD_TRANSFORM = "transform";

    private final CommandFactory commandFactory;

    /**
     * Constructs a new CommandLineApplication.
     *
     * @param commandFactory command factory to use
     */
    @Inject
    public CommandLineApplication(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * Command line application entry point.
     *
     * @param args args given by the user
     */
    public final void execute(final String[] args) {
        // definition
        JCommander jCommander = new JCommander();
        jCommander.setProgramName("spiner");

        HelpCommand helpCommand = commandFactory.createHelpCommand(jCommander);
        jCommander.addObject(helpCommand);

        VersionCommand versionCommand = commandFactory.createVersionCommand();
        jCommander.addObject(versionCommand);

        TransformCommand transformCommand = commandFactory.createTransformCommand();
        jCommander.addCommand(CMD_TRANSFORM, transformCommand);

        // parsing
        jCommander.parse(args);

        // interrogation
        if(versionCommand.isVersionCommand()) {
            versionCommand.run();
        } else if(jCommander.getParsedCommand() != null) {
            if(CMD_TRANSFORM.equals(jCommander.getParsedCommand())) {
                transformCommand.run();
            }
        } else {
            helpCommand.run();
        }
    }

    /**
     * Starts the command line application.
     *
     * See documentation on {@link CommandLineApplication} for list of accepted arguments.
     *
     * @param args see {@link CommandLineApplication} for list of accepted arguments
     */
    public static void main(final String[] args) {
        Injector injector = Guice.createInjector(new CommandLineModule());

        CommandLineApplication application = injector.getInstance(CommandLineApplication.class);
        application.execute(args);
    }

}
