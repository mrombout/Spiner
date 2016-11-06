package nl.mikero.spiner.core.transformer.epub;

import com.google.inject.Inject;
import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import nl.mikero.spiner.core.exception.TwineValidationFailedException;
import nl.mikero.spiner.core.twine.TwineRepairer;
import nl.mikero.spiner.core.inject.ArchiveRepairer;
import nl.mikero.spiner.core.inject.PublishedRepairer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.Objects;

/**
 * A facade to the Spine core functionalities.
 *
 * @deprecated use {@link TwineStoryEpubTransformer} instead
 */
@Deprecated
public class EpubTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpubTransformer.class);

    private final TwineRepairer publishedRepairer;
    private final TwineRepairer archiveRepairer;

    /**
     * Constructs a new EpubTransformer.
     *
     * @param publishedRepairer         repairer to use for published twine stories
     * @param archiveRepairer           repaired to use for twine archives
     */
    @Inject
    public EpubTransformer(@PublishedRepairer TwineRepairer publishedRepairer, @ArchiveRepairer TwineRepairer archiveRepairer) {
        this.publishedRepairer = Objects.requireNonNull(publishedRepairer);
        this.archiveRepairer = Objects.requireNonNull(archiveRepairer);
    }

    /**
     * Validate if the given file is in a valid XML format that Spiner understands.
     *
     * @param inputStream input stream to validate
     * @return returns {@code TRUE} if input document is valid, {@code FALSE} otherwise
     * @throws TwineValidationFailedException if validation failed during or before validation
     */
    public boolean validate(InputStream inputStream) throws TwineValidationFailedException {
        Objects.requireNonNull(inputStream);

        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(EpubTransformer.class.getResource("/format.xsd"));

            Validator validator = schema.newValidator();

            Source inputSource = new StreamSource(inputStream);
            validator.validate(inputSource);
        } catch(SAXException | IOException e) {
            throw new TwineValidationFailedException("Could not validate input stream", e);
        }

        return true;
    }

    /**
     * Returns whether the given input is a valid Twine Archive.
     *
     * @param inputStream input stream to check
     * @return returns {@code TRUE} if input document is a Twine Archive,
     * {@code FALSE} otherwise
     */
    public boolean isArchive(InputStream inputStream) {
        try {
            return isType(inputStream, archiveRepairer);
        } catch (TwineValidationFailedException | TwineRepairFailedException e) {
            LOGGER.warn("Could not validate input, assuming input is not an archive", e);
            return false;
        }
    }

    /**
     * Returns whether the given input is a published Twine story.
     *
     * @param inputStream input stream to check
     * @return returns {@code TRUE} if input document is a published Twine
     * story, {@code FALSE} otherwise
     */
    public boolean isPublished(InputStream inputStream) {
        try {
            return isType(inputStream, publishedRepairer);
        } catch (TwineValidationFailedException | TwineRepairFailedException e) {
            LOGGER.warn("Could not validate input, assuming input is not a published Twine story", e);
            return false;
        }
    }

    /**
     * Returns whether the given input can be successfully repaired by the given
     * {@link TwineRepairer}, and thus can be considered of a certain type.
     *
     * @param inputStream input stream to check
     * @param repairer    repairer to use
     * @return returns {@code TRUE} if input can be repaired and is validated
     * successfully, {@code FALSE} otherwise.
     */
    private boolean isType(InputStream inputStream, TwineRepairer repairer) throws TwineValidationFailedException {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(repairer);

        ByteArrayOutputStream repairedOutput = new ByteArrayOutputStream();
        try {
            byte[] inputBytes = IOUtils.toByteArray(inputStream);

            // repair input
            try (InputStream in = new ByteArrayInputStream(inputBytes)) {
                repairer.repair(in, repairedOutput);
            }

            // validate repaired result
            try (InputStream in = new ByteArrayInputStream(repairedOutput.toByteArray())) {
                return validate(in);
            }
        } catch (IOException e) {
            LOGGER.warn("Could not determine input type", e);
        } finally {
            try {
                repairedOutput.close();
            } catch (IOException e) {
                LOGGER.error("Could not close output stream for repaired output", e);
            }
        }

        return false;
    }
}
