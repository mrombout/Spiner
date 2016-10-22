package nl.mikero.spiner.frontend;

import javafx.concurrent.Task;
import nl.mikero.spiner.core.service.TwineService;

import java.io.*;

public class TransformTask extends Task<Void> {
    private final TwineService twineService;

    private final File inputFile;
    private final File outputFile;

    public TransformTask(TwineService twineService, File inputFile, File outputFile) {
        this.twineService = twineService;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    protected Void call() throws Exception {
        updateProgress(0, 1);
        try(InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            twineService.transform(in, out);
            updateProgress(1, 1);
        }

        return null;
    }
}
