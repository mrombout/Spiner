package nl.mikero.spiner.frontend;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;

import javafx.concurrent.Task;
import nl.mikero.spiner.frontend.io.FileInputOutputStream;

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
    public TransformTask(
            final TransformService transformService,
            final Transformer transformer,
            final File inputFile,
            final File outputFile) {
        super();
        this.transformService = transformService;
        this.transformer = transformer;

        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    protected final Void call() throws IOException {
        updateProgress(0, 1);

        try(FileInputOutputStream finout = new FileInputOutputStream(inputFile, outputFile);) {
            transformService.transform(finout.getInputStream(), finout.getOutputStream(), transformer);
        }

        updateProgress(1, 1);

        return null;
    }
}
