package nl.mikero.spiner.frontend;

import javafx.concurrent.Task;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;

import java.io.*;

/**
 * Transforms an input Twine XML file to an EPUB file.
 */
public class TransformTask extends Task<Void> {
    private final TransformService transformService;
    private final Transformer transformer;

    private final File inputFile;
    private final File outputFile;

    /**
     * Constructs a new TransformTask.
     *
     * @param transformService transformer service to use
     * @param transformer transformer to use
     * @param inputFile twine input file
     * @param outputFile transform output file
     */
    public TransformTask(TransformService transformService, Transformer transformer, File inputFile, File outputFile) {
        this.transformService = transformService;
        this.transformer = transformer;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    protected Void call() throws Exception {
        updateProgress(0, 1);
        try(InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            transformService.transform(in, out, transformer);
            updateProgress(1, 1);
        }

        return null;
    }
}
