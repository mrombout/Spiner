package nl.mikero.turntopassage.core;

import nl.mikero.turntopassage.core.exception.TwineRepairFailedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Repairs an input stream to a valid XML document that TurnToPassage
 * understands.
 */
public interface TwineRepairer {
    void repair(InputStream inputStream, OutputStream outputStream) throws TwineRepairFailedException;
}
