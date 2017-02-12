package nl.mikero.spiner.frontend.io;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provides an easy way to handle an input and an output stream by making sure they always close properly.
 */
public class FileInputOutputStream implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileInputOutputStream.class);

    private final FileInputStream input;
    private final FileOutputStream output;

    /**
     * Constructs a new FileInputOutputStream.
     *
     * @param inputFile input file to create stream from
     * @param outputFile output file to create stream from
     * @throws IOException if output stream can't be created
     */
    public FileInputOutputStream(final File inputFile, final File outputFile) throws IOException {
        input = FileUtils.openInputStream(inputFile);

        try {
            output = FileUtils.openOutputStream(outputFile);
        } catch (IOException e) {
            input.close();
            throw e;
        }
    }

    /**
     * Returns the input stream created from the `inputFile`.
     *
     * @return input stream created from the inputFile
     */
    public final FileInputStream getInputStream() {
        return input;
    }

    /**
     * Returns the output stream created from the `outputFile`.
     *
     * @return output stream created from the outputFile
     */
    public final FileOutputStream getOutputStream() {
        return output;
    }

    /**
     * Closes both the input and the output stream.
     *
     * Attempts to close both the input and output stream. If closing the input stream fails it will still try and close
     * the output stream. If that also fails, an exception is thrown.
     *
     * @throws IOException if either or both stream fail to close
     */
    @Override
    public final void close() throws IOException {
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            LOGGER.warn("Error closing FileInputOutputStream.", e);
            output.close();
        }
    }
}
