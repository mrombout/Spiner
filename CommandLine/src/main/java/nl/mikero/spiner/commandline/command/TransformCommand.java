package nl.mikero.spiner.commandline.command;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.output.CloseShieldOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transform Twine file to EPUB.
 */
@Parameters(separators = "=", commandDescription = "Transform Twine file to EPUB.")
public class TransformCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformCommand.class);

    private static final String ARG_FORMAT_EPUB = "epub";

    @Parameter(names = {"--format", "-f"}, description = "Format to transform to.")
    private String format = ARG_FORMAT_EPUB;

    @Parameter(names = {"--input", "-i"}, description = "File to transform to format.", required = true)
    private String inputPath;

    @Parameter(names = {"--output", "-o"}, description = "File to write to.")
    private String outputPath;

    @Parameter(names = {"--debug", "-x"}, description = "Show debug output.", help = true)
    private boolean showDebugOutput;

    private final TransformService transformService;
    private final TwineStoryEpubTransformer epubTransformer;

    /**
     * Constructs a new TransformCommand.
     *
     * @param transformService transform service to use
     * @param epubTransformer transform service to use to transform to epub
     */
    @Inject
    public TransformCommand(final TransformService transformService, final TwineStoryEpubTransformer epubTransformer) {
        this.transformService = transformService;
        this.epubTransformer = epubTransformer;
    }

    /**
     * Transform the input file to the output file in the given format.
     */
    @Override
    public final void run() {
        InputStream inputStream = new CloseShieldInputStream(System.in);
        OutputStream outputStream = new CloseShieldOutputStream(System.out);
        Transformer transformer = epubTransformer;

        FileInputStream fin = null;
        FileOutputStream fout = null;

        try {
            try {
                fin = new FileInputStream(new File(inputPath));
                inputStream = new BufferedInputStream(fin);
            } catch (FileNotFoundException e) {
                handleError(String.format("Input file %s could not be found.", inputPath), e, 1);
            }

            if (outputPath != null) {
                try {
                    fout = new FileOutputStream(new File(outputPath));
                    outputStream = new BufferedOutputStream(fout);
                } catch (FileNotFoundException e) {
                    handleError(String.format("Output file %s could not be found.", outputPath), e, 2);
                }
            }

            transformService.transform(inputStream, outputStream, transformer);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                if (fin != null)
                    fin.close();
                if (fout != null)
                    fout.close();
            } catch (IOException e) {
                handleError("Error closing streams.", e, 3);
            }
        }
    }

    /**
     * Prints out the error message and exits with the status code.
     *
     * @param msg    message to print to console
     * @param cause  cause of the error
     * @param status status code to exit with
     */
    private void handleError(final String msg, final Throwable cause, final int status) {
        System.err.println(msg);
        if (showDebugOutput)
            LOGGER.error(msg, cause);
        System.exit(status);
    }
}
