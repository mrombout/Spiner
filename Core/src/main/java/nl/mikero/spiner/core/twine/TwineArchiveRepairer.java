package nl.mikero.spiner.core.twine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import org.apache.commons.io.IOUtils;
import org.w3c.tidy.Tidy;

/**
 * Repairs a Twine Archive to create a valid XML document that Spiner
 * understands.
 */
public class TwineArchiveRepairer implements TwineRepairer {

    private final Tidy tidy;

    /**
     * Constructs a new TwineArchiveRepairer.
     */
    public TwineArchiveRepairer() {
        this.tidy = new Tidy();
        tidy.setInputEncoding(StandardCharsets.UTF_8.name());
        tidy.setOutputEncoding(StandardCharsets.UTF_8.name());
        tidy.setDocType("auto");
        tidy.setXmlOut(true);
        tidy.setXmlTags(true);

        Properties props = new Properties();
        props.setProperty("new-blocklevel-tags", "tw-storydata");
        props.setProperty("new-pre-tags", "tw-passagedata");
        tidy.getConfiguration().addProps(props);
    }

    /**
     * Repairs a Twine Archive to a valid XML document that Spiner
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
    public final void repair(final InputStream inputStream, final OutputStream outputStream) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);

        try {
            // start with XML declaration
            outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(StandardCharsets.UTF_8));

            // add tw-stories element
            outputStream.write("<tw-storiesdata>".getBytes(StandardCharsets.UTF_8));

            // copy original contents
            try (InputStream in = new ByteArrayInputStream(IOUtils.toByteArray(inputStream))) {
                // tidy input
                tidy.parse(in, outputStream);
            }

            // close tw-stories element
            outputStream.write("</tw-storiesdata>".getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            throw new TwineRepairFailedException("Could not repair input stream", e);
        }
    }

}
