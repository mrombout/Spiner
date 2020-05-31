package nl.mikero.spiner.commandline;

import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.epub.embedder.EmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwineArchiveRepairer;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import nl.mikero.spiner.core.twine.markdown.MarkdownProcessor;
import nl.mikero.spiner.core.twine.markdown.PegdownTransitionMarkdownRenderParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.output.CloseShieldOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineApplication.class);

    private final HelpFormatter formatter;
    private final VersionService versionService;
    private final TransformService transformService;
    private final TwineStoryEpubTransformer epubTransformer;

    private final Options options;
    private final Option helpOption;
    private final Option versionOption;
    private final Option debugOption;
    private final Option inputOption;
    private final Option outputOption;

    CommandLineApplication(VersionService versionService, TransformService transformService, TwineStoryEpubTransformer epubTransformer) {
        this.formatter = new HelpFormatter();
        this.versionService = versionService;
        this.transformService = transformService;
        this.epubTransformer = epubTransformer;

        options = new Options();

        helpOption = new Option("help", false, "display this help and exit");
        options.addOption(helpOption);

        versionOption = new Option("version", false, "display version information");
        options.addOption(versionOption);

        debugOption = new Option("debug", false, "show debug output");
        options.addOption(debugOption);

        inputOption = Option.builder("i")
                .longOpt("input")
                .hasArg()
                .argName("input")
                .desc("location of input HTML file")
                .build();
        options.addOption(inputOption);

        outputOption = Option.builder("o")
                .longOpt("output")
                .hasArg()
                .argName("output")
                .desc("location of output file")
                .build();
        options.addOption(outputOption);
    }

    /**
     * Command line application entry point.
     *
     * @param args args given by the user
     */
    public final void execute(final String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if(cmd.hasOption(helpOption.getOpt())) {
                doPrintHelp();
            } else if (cmd.hasOption(versionOption.getOpt())) {
                doPrintVersion();
            } else if (cmd.hasOption(inputOption.getOpt())) {
                doTransform(cmd);
            }
        } catch (ParseException e) {
            System.out.println(String.format("invalid format: %s", e.getMessage()));
            doPrintHelp();
        }
    }

    private void doTransform(CommandLine cmd) {
        InputStream inputStream = new CloseShieldInputStream(System.in);
        OutputStream outputStream = new CloseShieldOutputStream(System.out);

        try {
            String inputPath = inputOption.getValue();
            try {
                FileInputStream fin = new FileInputStream(new File(inputPath));
                inputStream = new BufferedInputStream(fin);
            } catch (FileNotFoundException e) {
                handleError(String.format("Input file %s could not be found.", inputPath), e, 1, cmd);
            }

            String outputPath = inputOption.getValue();
            if (outputPath != null) {
                try {
                    FileOutputStream fout = new FileOutputStream(new File(outputPath));
                    outputStream = new BufferedOutputStream(fout);
                } catch (FileNotFoundException e) {
                    handleError(String.format("Output file %s could not be found.", outputPath), e, 2, cmd);
                }
            }

            transformService.transform(inputStream, outputStream, epubTransformer);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("error closing input stream", e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                 LOGGER.error("error closing output stream", e);
            }
        }
    }

    private void doPrintHelp() {
        formatter.printHelp("java -jar spiner.jar [--help | --version] [-i <input>] [-o <file>]", options);
    }

    private void doPrintVersion() {
        System.out.println("Spiner " + versionService.get() + "\n" +
                "Copyright (C) 2015 Mike Rombout\n" +
                "License GPLv3+: GNU GPL isVersion 3 or later <http://gnu.org/licenses/gpl.html>\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extend permitted by law.\n");
    }

    private void handleError(final String msg, final Throwable cause, final int status, final CommandLine cmd) {
        System.err.println(msg);
        if (cmd.hasOption(debugOption.getOpt()))
            LOGGER.error(msg, cause);
        System.exit(status);
    }

    /**
     * Starts the command line application.
     *
     * See documentation on {@link CommandLineApplication} for list of accepted arguments.
     *
     * @param args see {@link CommandLineApplication} for list of accepted arguments
     */
    public static void main(final String[] args) {
        GradleVersionService gradleVersionService = new GradleVersionService();
        TwineArchiveRepairer twineArchiveRepairer = new TwineArchiveRepairer();
        ExtendTwineXmlTransformer extendTwineXmlTransformer = new ExtendTwineXmlTransformer();
        TwineArchiveParser twineArchiveParser = new TwineArchiveParser();
        TransformService transformService = new TransformService(twineArchiveRepairer, extendTwineXmlTransformer, twineArchiveParser);
        MarkdownProcessor markdownProcessor = new PegdownTransitionMarkdownRenderParser();
        EmbedderFactory embedderFactory = new HashEmbedderFactory(DigestUtils.getSha256Digest());
        ResourceEmbedder resourceEmbedder = new ResourceEmbedder(embedderFactory);
        TwineStoryEpubTransformer twineStoryEpubTransformer = new TwineStoryEpubTransformer(markdownProcessor, resourceEmbedder);

        CommandLineApplication application = new CommandLineApplication(gradleVersionService, transformService, twineStoryEpubTransformer);
        application.execute(args);
    }

}
