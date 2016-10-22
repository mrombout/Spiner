package nl.mikero.spiner.core;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Repairs an input stream to a valid XML document that Spiner
 * understands.
 */
public interface TwineRepairer {
    void repair(InputStream inputStream, OutputStream outputStream) throws TwineRepairFailedException;
}
