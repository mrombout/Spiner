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

    private final InputStream inputStream;
    private final OutputStream outputStream;

    /**
     * Constructs a new TransformTask.
     *
     * @param transformService transformer service to use
     * @param transformer transformer to use
     * @param inputStream twine input file
     * @param outputStream transform output file
     */
    public TransformTask(
            final TransformService transformService,
            final Transformer transformer,
            final InputStream inputStream,
            final OutputStream outputStream) {
        super();
        this.transformService = transformService;
        this.transformer = transformer;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    protected final Void call() {
        updateProgress(0, 1);
        transformService.transform(inputStream, outputStream, transformer);
        updateProgress(1, 1);

        return null;
    }
}
