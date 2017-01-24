package nl.mikero.spiner.commandline.goal;

import com.google.inject.Inject;
import nl.mikero.spiner.commandline.annotation.DefineOption;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.latex.LatexTransformer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.output.CloseShieldOutputStream;

import java.io.*;

@DefineOption(
        opt = "f",
        longOpt = "format",
        description = "output format, one of (epub|latex)",
        hasArg = true,
        argName = "format"
)
@DefineOption(
        opt = "i",
        longOpt = "input",
        description = "location of input HTML file",
        hasArg = true,
        argName = "input"
)
@DefineOption(
        opt = "o",
        longOpt = "output",
        description = "location of output file",
        hasArg = true,
        argName = "output"
)
@DefineOption(
        opt = "d",
        longOpt = "debug",
        description = "display detailed output when an error occurs"
)
public class TransformGoal implements Goal {
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
    public TransformGoal(TransformService transformService, TwineStoryEpubTransformer epubTransformer, LatexTransformer latexTransformer) {
        this.transformService = transformService;
        this.epubTransformer = epubTransformer;
        this.latexTransformer = latexTransformer;
    }

    @Override
    public void execute(CommandLine cmd, Options options) {
        InputStream inputStream = new CloseShieldInputStream(System.in);
        OutputStream outputStream = new CloseShieldOutputStream(System.out);
        Transformer transformer = epubTransformer;

        FileInputStream fin = null;
        FileOutputStream fout = null;

        try {
            if (cmd.hasOption(OPT_INPUT)) {
                String fileArg = cmd.getOptionValue(OPT_INPUT);
                try {
                    fin = new FileInputStream(new File(fileArg));
                    inputStream = new BufferedInputStream(fin);
                } catch (FileNotFoundException e) {
                    handleError(String.format("Input file %s could not be found.", fileArg), e, 1);
                }
            }
            if (cmd.hasOption(OPT_OUTPUT)) {
                String outputArg = cmd.getOptionValue(OPT_OUTPUT);
                try {
                    fout = new FileOutputStream(new File(outputArg));
                    outputStream = new BufferedOutputStream(fout);
                } catch (FileNotFoundException e) {
                    handleError(String.format("Output file %s could not be found.", outputArg), e, 1);
                }
            }
            if(cmd.hasOption(OPT_FORMAT)) {
                String formatArg = cmd.getOptionValue(OPT_FORMAT);
                if(formatArg.equals(ARG_FORMAT_LATEX))
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
