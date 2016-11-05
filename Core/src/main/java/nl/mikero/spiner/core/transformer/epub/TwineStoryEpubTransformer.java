package nl.mikero.spiner.core.transformer.epub;

import com.google.inject.Inject;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.exception.TwineTransformationWriteException;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkRenderer;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.apache.commons.io.output.NullOutputStream;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Transforms a {@link TwStorydata} object to an EPUB file.
 */
public class TwineStoryEpubTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwineStoryEpubTransformer.class);
    private static final String EXTENSION = "epub";

    private final PegDownProcessor pdProcessor;
    private final TwineLinkRenderer twineLinkRenderer;
    private final ResourceEmbedder resourceEmbedder;

    private final Tidy tidy;

    /**
     * Constructs a new TwineStoryEpubTransformer using the given parameters.
     *
     * @param pdProcessor pegdown processor to use, may not be null
     * @param twineLinkRenderer link renderer to use, may not be null
     * @param resourceEmbedder resource embedder to use, may not be null
     */
    @Inject
    public TwineStoryEpubTransformer(PegDownProcessor pdProcessor, TwineLinkRenderer twineLinkRenderer, ResourceEmbedder resourceEmbedder) {
        this.pdProcessor = Objects.requireNonNull(pdProcessor);
        this.twineLinkRenderer = Objects.requireNonNull(twineLinkRenderer);
        this.resourceEmbedder = Objects.requireNonNull(resourceEmbedder);

        this.tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setDocType("omit");
        tidy.setXHTML(true);
    }

    /**
     * Transforms a {@link TwStorydata} object to an EPUB file, using the default options.
     *
     * @param story        twine archive xml to transform
     * @param outputStream output stream to write epub to
     */
    @Override
    public void transform(TwStorydata story, OutputStream outputStream) {
        transform(story, outputStream, story.getXtwMetadata() != null ? EpubTransformOptions.fromXtwMetadata(story.getXtwMetadata()) : EpubTransformOptions.EMPTY);
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }

    /**
     * Transforms a {@link TwStorydata} object to an EPUB file.
     *
     * @param story twine xml to transform, may not be null
     * @param outputStream output stream to write EPUB to, may not be null
     * @param options transform options, contains EPUB metadata, may not me null
     */
    public void transform(TwStorydata story, OutputStream outputStream, EpubTransformOptions options) {
        Objects.requireNonNull(story);
        Objects.requireNonNull(outputStream);
        Objects.requireNonNull(options);

        // create new Book
        Book book = new Book();

        // set metadata
        book.setMetadata(options.getMetadata());

        // add the title
        book.getMetadata().addTitle(story.getName());

        // add stylesheet resources
        if(story.getStyle() != null) {
            Resource stylesheetResource = new Resource(null, story.getStyle().getValue().getBytes(), "Story.css", MediatypeService.CSS);
            book.getResources().add(stylesheetResource);
        }
        
        // embed all resources
        embedResources(book, story);

        // add all passages
        try {
            for (TwPassagedata passage : story.getTwPassagedata()) {
                // add passage as chapter
                String passageContent = transformPassageTextToXhtml(passage.getValue());
                Resource passageResource = new Resource(null, passageContent.getBytes(StandardCharsets.UTF_8), passage.getName() + ".xhtml", MediatypeService.XHTML);

                book.addSection(passage.getName(), passageResource);
            }
        } catch (TransformerException e) {
            throw new TwineTransformationFailedException("Could not transform document", e);
        }

        try {
            // write to stream
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, outputStream);
        } catch (IOException e) {
            throw new TwineTransformationWriteException("Could not write transformed input to output stream", e);
        }
    }

    private void embedResources(Book book, TwStorydata story) {
        for(TwPassagedata passage : story.getTwPassagedata()) {
            RootNode rootNode = pdProcessor.parseMarkdown(passage.getValue().toCharArray());
            resourceEmbedder.embed(book, rootNode);
        }
    }

    /**
     * Transforms the text of a passage to a valid XHTML document.
     *
     * @return a valid xhtml document containing the passage text in the body
     */
    private String transformPassageTextToXhtml(String passageText) throws TransformerException {
        String xhtml = pdProcessor.markdownToHtml(passageText, twineLinkRenderer);

        try(InputStream in = new ByteArrayInputStream(xhtml.getBytes(StandardCharsets.UTF_8)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = tidy.parseDOM(in, new NullOutputStream());
            Node head = document.getElementsByTagName("head").item(0);

            // add stylesheet
            Element style = document.createElement("link");
            style.setAttribute("type", "text/css");
            style.setAttribute("rel", "stylesheet");
            style.setAttribute("href", "Story.css");
            head.appendChild(style);

            // add ttp class to body
            Element body = (Element) document.getElementsByTagName("body").item(0);
            body.setAttribute("class", body.getAttribute("class") + " ttp");

            // transform xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(out);

            transformer.transform(source, result);

            // set return value
            xhtml = out.toString();
        } catch (IOException e) {
            // NOTE: Do nothing, ByteArrayOutputStream never throws IOException on close
            LOGGER.error("Could not close output stream.", e);
        }

        return xhtml;
    }
}
