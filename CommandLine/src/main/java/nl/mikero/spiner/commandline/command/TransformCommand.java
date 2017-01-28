package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.latex.LatexTransformer;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.output.CloseShieldOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

@Parameters(separators = "=", commandDescription = "Transform Twine file to EPUB of LaTeX.")
public class TransformCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformCommand.class);

    private static final String ARG_FORMAT_EPUB = "epub";
    private static final String ARG_FORMAT_LATEX = "latex";

    @Parameter(names = {"--format", "-f"}, description = "Format to transform to.")
    private String format = ARG_FORMAT_EPUB;

    @Parameter(names = {"--input", "-i"}, description = "File to transform to format.", required = true)
    private String inputPath;

    @Parameter(names = {"--output", "-o"}, description = "File to write to.")
    private String outputPath;

    @Parameter(names = {"--debug", "-x"}, description = "Show debug output.", help = true)
    private boolean showDebugOutput = false;

    private final TransformService transformService;
    private final TwineStoryEpubTransformer epubTransformer;
    private final LatexTransformer latexTransformer;

    @Inject
    public TransformCommand(
            final TransformService transformService,
            final TwineStoryEpubTransformer epubTransformer,
            final LatexTransformer latexTransformer) {
        this.transformService = transformService;
        this.epubTransformer = epubTransformer;
        this.latexTransformer = latexTransformer;
    }

    @Override
    public void run() {
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

            if (format != null) {
                if (format.equals(ARG_FORMAT_LATEX))
                    transformer = latexTransformer;
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
        System.out.println(msg);
        if (showDebugOutput)
            LOGGER.error(msg, cause);
        System.exit(status);
    }
}
