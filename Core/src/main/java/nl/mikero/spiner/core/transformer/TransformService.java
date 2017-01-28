package nl.mikero.spiner.core.transformer;

import com.google.inject.Inject;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.inject.PublishedRepairer;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwineRepairer;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import nl.mikero.spiner.core.twine.model.TwStoriesdata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Objects;

/**
 * Transforms an {@link InputStream} into a different format using a {@link Transformer} and outputs it to an
 * {@link OutputStream}.
 */
public class TransformService {
    private final TwineRepairer publishedRepairer;
    private final ExtendTwineXmlTransformer extendTwineXmlTransformer;
    private final TwineArchiveParser twineArchiveParser;

    /**
     * Constructs a new TransformService.
     *
     * @param publishedRepairer repairer to use for published twine stories
     * @param extendTwineXmlTransformer twine extended to use
     * @param twineArchiveParser twine archive parser to use
     */
    @Inject
    public TransformService(@PublishedRepairer TwineRepairer publishedRepairer, ExtendTwineXmlTransformer extendTwineXmlTransformer, TwineArchiveParser twineArchiveParser) {
        this.publishedRepairer = publishedRepairer;
        this.extendTwineXmlTransformer = extendTwineXmlTransformer;
        this.twineArchiveParser = twineArchiveParser;
    }

    /**
     * Transforms a published Twine story or Twine archive into another format.
     *
     * @param inputStream input stream to a published Twine story or archive
     * @param outputStream output stream to write the transformed input to
     * @param transformer transformer to use
     * @throws TwineTransformationFailedException when transformation fails
     */
    public void transform(InputStream inputStream, OutputStream outputStream, Transformer transformer) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);
        Objects.requireNonNull(transformer);

        try (ByteArrayOutputStream repairedInput = new ByteArrayOutputStream();
             ByteArrayOutputStream transformedInput = new ByteArrayOutputStream()) {
            repair(inputStream, repairedInput);
            extend(repairedInput, transformedInput);

            TwStoriesdata stories = parse(transformedInput);
            for (TwStorydata twStorydata : stories.getTwStorydata()) {
                transformer.transform(twStorydata, outputStream);
            }
        } catch (Exception e) {
            throw new TwineTransformationFailedException("Could not transform input.", e);
        }
    }

    private void repair(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (InputStream in = new ByteArrayInputStream(IOUtils.toByteArray(inputStream))) {
            publishedRepairer.repair(in, outputStream);
        }
    }

    private void extend(ByteArrayOutputStream repairedOutput, OutputStream transformedOutput) throws IOException {
        try(InputStream in = new ByteArrayInputStream(repairedOutput.toByteArray())) {
            extendTwineXmlTransformer.transform(in, transformedOutput);
    }
    }

    private TwStoriesdata parse(ByteArrayOutputStream transformedInput) throws IOException {
        try (InputStream in = new ByteArrayInputStream(transformedInput.toByteArray())) {
            return twineArchiveParser.parse(in);
        }
    }
}
