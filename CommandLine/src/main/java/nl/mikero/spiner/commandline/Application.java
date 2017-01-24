package nl.mikero.spiner.commandline;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.commandline.annotation.DefineOption;
import nl.mikero.spiner.commandline.annotation.DefineOptions;
import nl.mikero.spiner.commandline.goal.Goal;
import nl.mikero.spiner.commandline.inject.TwineModule;
import org.apache.commons.cli.*;
import org.apache.commons.cli.Options;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private final Set<Goal> goals;

    @Inject
    public Application(Set<Goal> goals) {
        this.goals = goals;
    }


    private void execute(String[] args) throws ParseException, IllegalAccessException, InstantiationException {
        Map<Option, Goal> definedGoals = new HashMap<>();

        // definition
        Options options = new Options();

        for(Goal goal : goals) {
            Class<? extends Goal> goalClass = goal.getClass();
            if(goalClass.isAnnotationPresent(DefineOptions.class)) {
                DefineOptions optionsAnnotation = goalClass.getAnnotation(DefineOptions.class);

                for(DefineOption optionAnnotation : optionsAnnotation.value()) {
                    Option option = createOptionFromAnnotation(optionAnnotation);
                    options.addOption(option);

                    definedGoals.put(option, goal);
                }
            } else if(goalClass.isAnnotationPresent(DefineOption.class)) {
                DefineOption optionAnnotation = goalClass.getAnnotation(DefineOption.class);

                Option option = createOptionFromAnnotation(optionAnnotation);
                options.addOption(option);

                definedGoals.put(option, goal);
            }
        }

        // parsing
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cmd = commandLineParser.parse(options, args);

        // interrogation
        for(Map.Entry<Option, Goal> entry : definedGoals.entrySet()) {
            if(cmd.hasOption(entry.getKey().getOpt())) {
                entry.getValue().execute(cmd, options);
                System.exit(0);
            }
        }
    }

    private Option createOptionFromAnnotation(DefineOption optionAnnotation) {
        return Option.builder(optionAnnotation.opt())
                .longOpt(optionAnnotation.longOpt())
                .hasArg(optionAnnotation.hasArg())
                .argName(optionAnnotation.argName())
                .required(optionAnnotation.required())
                .desc(optionAnnotation.description())
                .numberOfArgs(optionAnnotation.numberOrArgs())
                .optionalArg(optionAnnotation.optionalArg())
                .valueSeparator(optionAnnotation.valueSeperator())
                .build();
    }

    /**
     * Starts the command line application.
     *
     * See documentation on {@link Application} for list of accepted arguments.
     *
     * @see Application
     * @param args see {@link Application} for list of accepted arguments
     */
    public static void main(String[] args) throws ParseException, IllegalAccessException, InstantiationException {
        Injector injector = Guice.createInjector(new TwineModule());

        Application application = injector.getInstance(Application.class);
        application.execute(args);
    }

}
