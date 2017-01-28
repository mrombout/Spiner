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

import java.io.*;

@Parameters(separators = "=", commandDescription = "Transform Twine file to EPUB of LaTeX.")
public class TransformCommand implements Command {
    @Parameter(names = {"--format", "-f"}, description = "Format to transform to.")
    public String format = ARG_FORMAT_EPUB;

    @Parameter(names = {"--input", "-i"}, description = "File to transform to format.", required = true)
    public String input;

    @Parameter(names = {"--output", "-o"}, description = "File to write to.")
    public String output;

    public static final String ARG_FORMAT_EPUB = "epub";
    public static final String ARG_FORMAT_LATEX = "latex";

    private static final String OPT_INPUT = "file";
    private static final String OPT_OUTPUT = "output";
    private static final String OPT_FORMAT = "format";

    private final TransformService transformService;
    private final TwineStoryEpubTransformer epubTransformer;
    private final LatexTransformer latexTransformer;

    private boolean showDebugOutput = false;

    @Inject
    public TransformCommand(TransformService transformService, TwineStoryEpubTransformer epubTransformer, LatexTransformer latexTransformer) {
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
                fin = new FileInputStream(new File(input));
                inputStream = new BufferedInputStream(fin);
            } catch (FileNotFoundException e) {
                handleError(String.format("Input file %s could not be found.", input), e, 1);
            }

            if (output != null) {
                try {
                    fout = new FileOutputStream(new File(output));
                    outputStream = new BufferedOutputStream(fout);
                } catch (FileNotFoundException e) {
                    handleError(String.format("Output file %s could not be found.", output), e, 2);
                }
            }

            if(format != null) {
                if(format.equals(ARG_FORMAT_LATEX))
                    transformer = latexTransformer;
            }

            transformService.transform(inputStream, outputStream, transformer);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                if(fin != null)
                    fin.close();
                if(fout != null)
                    fout.close();
            } catch (IOException e) {
                 // TODO: Handle properly
            }
        }
    }

    private void handleError(String msg, Throwable throwable, int status) {
        System.out.println(msg);
        if(showDebugOutput)
            throwable.printStackTrace();
        System.exit(status);
    }
}
