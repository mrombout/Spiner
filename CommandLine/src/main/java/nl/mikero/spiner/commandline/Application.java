package nl.mikero.spiner.commandline;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.commandline.command.*;
import nl.mikero.spiner.commandline.factory.CommandFactory;
import nl.mikero.spiner.commandline.inject.TwineModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private CommandFactory commandFactory;

    @Inject
    public Application(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void execute(String[] args) {
        // definition
        JCommander jCommander = new JCommander();
        jCommander.setProgramName("spiner");

        HelpCommand helpCommand = commandFactory.createHelpCommand(jCommander);
        jCommander.addObject(helpCommand);

        VersionCommand versionCommand = commandFactory.createVersionCommand();
        jCommander.addObject(versionCommand);

        TransformCommand transformCommand = commandFactory.createTransformCommand();
        jCommander.addCommand("transform", transformCommand);

        // parsing
        jCommander.parse(args);

        // interrogation
        if(helpCommand.help) {
            helpCommand.run();
        } else if(versionCommand.version) {
            versionCommand.run();
        } else if(jCommander.getParsedCommand() != null ) {
            if(jCommander.getParsedCommand().equals("transform")) {
                transformCommand.run();
            }
        } else {
            helpCommand.run();
        }
    }

    /**
     * Starts the command line application.
     *
     * See documentation on {@link Application} for list of accepted arguments.
     *
     * @see Application
     * @param args see {@link Application} for list of accepted arguments
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TwineModule());

        Application application = injector.getInstance(Application.class);
        application.execute(args);
    }

}
