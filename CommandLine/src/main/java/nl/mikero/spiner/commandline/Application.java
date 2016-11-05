package nl.mikero.spiner.commandline;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.commandline.inject.TwineModule;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.EpubTransformOptions;
import nl.mikero.spiner.core.transformer.epub.EpubTransformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

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

    private static final String OPT_FILE = "file";
    private static final String OPT_OUTPUT = "output";
    private static final String OPT_TYPE = "type";
    private static final String OPT_HELP = "help";
    private static final String OPT_VERSION = "version";

    private final TransformService transformService;
    private final TwineStoryEpubTransformer epubTransformer;

    /**
     * Constructs a new command line spiner application.
     *
     * @param transformService transform service to use
     */
    @Inject
    public Application(TransformService transformService, TwineStoryEpubTransformer epubTransformer) {
        this.transformService = transformService;
        this.epubTransformer = epubTransformer;
    }

    private void execute(String[] args) {
        // options
        final Options options = new Options();

        Option input = OptionBuilder.hasArg()
                .withArgName("file")
                .withDescription("location of input HTML file")
                .withLongOpt(OPT_FILE)
                .create('f');
        options.addOption(input);

        Option output = OptionBuilder.hasArg()
                .withArgName("file")
                .withDescription("location of output EPUB file")
                .withLongOpt(OPT_OUTPUT)
                .create('o');
        options.addOption(output);

        Option type = OptionBuilder.hasArg()
                .withArgName("type")
                .withDescription(String.format("output type, one of (epub|latex)")) // TODO: load available options dynamically
                .withLongOpt(OPT_TYPE)
                .create('t');
        options.addOption(type);

        OptionGroup infoGroup = new OptionGroup();
        infoGroup.addOption(new Option(OPT_HELP, "display this help and exit"));
        infoGroup.addOption(new Option(OPT_VERSION, "output version information and exit"));
        options.addOptionGroup(infoGroup);

        // parse
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(OPT_VERSION)) {
                doOptVersion();
            } else if (cmd.hasOption(OPT_HELP)) {
                doOptHelp(options);
            } else {
                doTransform(cmd);
            }
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
        }

        System.exit(1);
    }

    private void doOptVersion() {
        System.out.println("Spiner 0.0.1");
        System.out.println("Copyright (C) 2015 Mike Rombout");
        System.out.println("License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>");
        System.out.println("This is free software: you are free to change and redistribute it.");
        System.out.println("There is NO WARRANTY, to the extend permitted by law.");
        System.exit(0);
    }

    private void doOptHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("spiner", options, true);
        System.exit(0);
    }

    private void doTransform(CommandLine cmd) throws ParserConfigurationException, TransformerException, SAXException, JAXBException, IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;

        if (cmd.hasOption(OPT_FILE)) {
            String fileArg = cmd.getOptionValue(OPT_FILE);
            try {
                inputStream = new BufferedInputStream(new FileInputStream(new File(fileArg)));
            } catch (FileNotFoundException e) {
                LOGGER.error("Input file {} could not be found.", fileArg, e);
                System.exit(1);
            }
        }
        if (cmd.hasOption(OPT_OUTPUT)) {
            String outputArg = cmd.getOptionValue(OPT_OUTPUT);
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputArg)));
            } catch (FileNotFoundException e) {
                LOGGER.error("Output file {} could not be found.", outputArg, e);
                System.exit(1);
            }
        }

        transformService.transform(inputStream, outputStream, epubTransformer);
        System.exit(0);
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
