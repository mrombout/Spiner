package nl.mikero.spiner.core;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Repairs an input stream to a valid XML document that Spiner
 * understands.
 */
public interface TwineRepairer {
    /**
     * Repairs a Twine output format to a valid XML document that Spiner understands.
     *
     * @param inputStream some input
     * @param outputStream repaired xml output
     */
    void repair(InputStream inputStream, OutputStream outputStream);
}
