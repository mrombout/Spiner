package nl.mikero.turntopassage.core.service;

import com.google.inject.Inject;
import nl.mikero.turntopassage.core.TwineArchiveParser;
import nl.mikero.turntopassage.core.TwineRepairer;
import nl.mikero.turntopassage.core.exception.TwineRepairFailedException;
import nl.mikero.turntopassage.core.exception.TwineValidationFailedException;
import nl.mikero.turntopassage.core.transformer.TwineStoryEpubTransformer;
import nl.mikero.turntopassage.core.inject.ArchiveRepairer;
import nl.mikero.turntopassage.core.inject.PublishedRepairer;
import nl.mikero.turntopassage.core.model.TwStoriesdata;
import nl.mikero.turntopassage.core.model.TwStorydata;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.Objects;

/**
 * A facade to the TurnToPassage core functionalities.
 */
public class TwineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwineService.class);

    private final TwineRepairer publishedRepairer;
    private final TwineRepairer archiveRepairer;

    private final TwineStoryEpubTransformer twineStoryEpubTransformer;
    private final TwineArchiveParser twineArchiveParser;

    /**
     * Constructs a new TwineService.
     *
     * @param publishedRepairer         repairer to use for published twine stories
     * @param archiveRepairer           repaired to use for twine archives
     * @param twineStoryEpubTransformer transformer to use
     * @param twineArchiveParser        twine archive parse to use
     */
    @Inject
    public TwineService(@PublishedRepairer TwineRepairer publishedRepairer, @ArchiveRepairer TwineRepairer archiveRepairer, TwineStoryEpubTransformer twineStoryEpubTransformer, TwineArchiveParser twineArchiveParser) {
        this.publishedRepairer = Objects.requireNonNull(publishedRepairer);
        this.archiveRepairer = Objects.requireNonNull(archiveRepairer);
        this.twineStoryEpubTransformer = Objects.requireNonNull(twineStoryEpubTransformer);
        this.twineArchiveParser = Objects.requireNonNull(twineArchiveParser);
    }

    /**
     * Transform published Twine story to an EPUB file.
     *
     * @param input  input stream to a published Twine story
     * @param output output stream to write the epub to
     */
    public void transform(InputStream input, OutputStream output) {
        transform(input, output, TransformOptions.EMPTY);
    }

    public void transform(InputStream input, OutputStream output, TransformOptions options) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);

        ByteArrayOutputStream repairedOutput = new ByteArrayOutputStream();

        try {
            byte[] inputBytes = IOUtils.toByteArray(input);

            // repair document
            try (InputStream in = new ByteArrayInputStream(inputBytes)) {
                publishedRepairer.repair(in, repairedOutput);
            }

            // parse and serialize
            try (InputStream in = new ByteArrayInputStream(repairedOutput.toByteArray())) {
                TwStoriesdata twStories = twineArchiveParser.parse(in);
                for (TwStorydata twStorydata : twStories.getTwStorydata()) {
                    twineStoryEpubTransformer.transform(twStorydata, output, options);
                }
            }
        } catch (SAXException | TwineRepairFailedException | IOException | JAXBException e) {
            LOGGER.error("Could not repair document in input stream", e);
        } finally {
            try {
                repairedOutput.close();
            } catch (IOException e) {
                LOGGER.error("Could not close repaired output stream", e);
            }
        }
    }

    /**
     * Validate if the given file is in a valid XML format that TurnToPassage understands.
     *
     * @param inputStream input stream to validate
     * @return returns {@code TRUE} if input document is valid, {@code FALSE} otherwise
     * @throws TwineValidationFailedException if validation failed during or before validation
     */
    public boolean validate(InputStream inputStream) throws TwineValidationFailedException {
        Objects.requireNonNull(inputStream);

        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(TwineService.class.getResource("/format.xsd"));

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
    private boolean isType(InputStream inputStream, TwineRepairer repairer) throws TwineValidationFailedException, TwineRepairFailedException {
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
