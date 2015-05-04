package nl.mikero.turntopassage.core.transformer;

import com.google.inject.Inject;
import nl.mikero.turntopassage.core.exception.TwineTransformationWriteException;
import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.mikero.turntopassage.core.model.TwStorydata;
import nl.mikero.turntopassage.core.pegdown.plugin.TwineLinkRenderer;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Transforms a {@link nl.mikero.turntopassage.core.model.TwStorydata} object to an EPUB file.
 */
public class TwineStoryEpubTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwineStoryEpubTransformer.class);

    private final PegDownProcessor pdProcessor;
    private final TwineLinkRenderer twineLinkRenderer;

    private final Tidy tidy;

    /**
     * Constructs a new TwineStoryEpubTransformer using the given parameters.
     *
     * @param pdProcessor       pegdown processor to use
     * @param twineLinkRenderer link renderer to use
     */
    @Inject
    public TwineStoryEpubTransformer(PegDownProcessor pdProcessor, TwineLinkRenderer twineLinkRenderer) {
        this.pdProcessor = Objects.requireNonNull(pdProcessor);
        this.twineLinkRenderer = Objects.requireNonNull(twineLinkRenderer);

        this.tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
    }

    /**
     * Transforms a {@link TwStorydata} object to an EPUB file.
     *
     * @param story        twine archive xml to transform
     * @param outputStream output stream to write epub to
     */
    public void transform(TwStorydata story, OutputStream outputStream) {
        Objects.requireNonNull(story);
        Objects.requireNonNull(outputStream);

        // create new Book
        Book book = new Book();

        // add the title
        book.getMetadata().addTitle(story.getName());

        // add all passages
        for (TwPassagedata passage : story.getTwPassagedata()) {
            // add passage as chapter
            String passageContent = transformPassageTextToXhtml(passage.getValue());
            Resource passageResource = new Resource(null, passageContent.getBytes(StandardCharsets.UTF_8), passage.getName() + ".xhtml", MediatypeService.XHTML);

            book.addSection(passage.getName(), passageResource);
        }

        try {
            // write to stream
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, outputStream);
        } catch (IOException e) {
            throw new TwineTransformationWriteException("Could not write transformed input to output stream", e);
        }
    }

    /**
     * Transforms the text of a passage to a valid XHTML document.
     *
     * @return a valid xhtml document containing the passage text in the body
     */
    private String transformPassageTextToXhtml(String passageText) {
        String xhtml = pdProcessor.markdownToHtml(passageText, twineLinkRenderer);

        try(InputStream in = new ByteArrayInputStream(xhtml.getBytes(StandardCharsets.UTF_8)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            tidy.parseDOM(in, out);
            xhtml = out.toString();
        } catch (IOException e) {
            // NOTE: Do nothing, ByteArrayOutputStream never throws IOException on close
            LOGGER.error("Could not close output stream.", e);
        }

        return xhtml;
    }

}
