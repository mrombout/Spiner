package nl.mikero.turntopassage.core;

import nl.mikero.turntopassage.core.exception.TwineRepairFailedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Repairs a Twine Archive to create a valid XML document that TurnToPassage
 * understands.
 */
public class TwineArchiveRepairer implements TwineRepairer {

    /**
     * Repairs a Twine Archive to a valid XML document that TurnToPassage
     * understands.
     * <p>
     * This class repairs a standard Twine Archive file and "repairs" it to a
     * well-formed XML document by applying the following steps:
     * <p>
     * <ol>
     * <li>Add XML declaration</li>
     * <li>Surround original content within {@code <tw-storiesdata>}
     * element</li>
     * </ol>
     *
     * @param inputStream  input stream to read from, typically a FileInputStream
     * @param outputStream output stream to write to, typically a FileOutputStream
     * @throws TwineRepairFailedException when the input stream can not be repaired
     */
    @Override
    public void repair(InputStream inputStream, OutputStream outputStream) throws TwineRepairFailedException {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);

        try {
            // start with XML declaration
            outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(StandardCharsets.UTF_8));

            // add tw-stories element
            outputStream.write("<tw-storiesdata>".getBytes(StandardCharsets.UTF_8));

            // copy original contents
            int character;
            while ((character = inputStream.read()) >= 0) {
                outputStream.write(character);
            }

            // close tw-stories element
            outputStream.write("</tw-storiesdata>".getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            throw new TwineRepairFailedException("Could not repair input stream", e);
        }
    }

}
