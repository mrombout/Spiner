package nl.mikero.spiner.core.transformer.epub;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.google.inject.Inject;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.exception.TwineTransformationWriteException;
import nl.mikero.spiner.core.transformer.SafeXmlFactory;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.twine.markdown.MarkdownProcessor;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Transforms a {@link TwStorydata} object to an EPUB file.
 */
public class TwineStoryEpubTransformer implements Transformer {
    private static final String EXTENSION = "epub";

    private static final String ATTR_CLASS = "class";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_REL = "rel";
    private static final String ATTR_HREF = "href";

    private static final String ID_AUTO_GEN = null;
    private static final String STORY_STYLESHEET_FILE = "Story.css";

    private final MarkdownProcessor pdProcessor;
    private final ResourceEmbedder resourceEmbedder;

    /**
     * Constructs a new TwineStoryEpubTransformer using the given parameters.
     *
     * @param pdProcessor pegdown processor to use, may not be null
     * @param resourceEmbedder resource embedder to use, may not be null
     */
    @Inject
    public TwineStoryEpubTransformer(
            final MarkdownProcessor pdProcessor,
            final ResourceEmbedder resourceEmbedder) {
        this.pdProcessor = Objects.requireNonNull(pdProcessor);
        this.resourceEmbedder = Objects.requireNonNull(resourceEmbedder);
    }

    /**
     * Transforms a {@link TwStorydata} object to an EPUB file, using the default options.
     *
     * @param story        twine archive xml to transform
     * @param outputStream output stream to write epub to
     */
    @Override
    public final void transform(final TwStorydata story, final OutputStream outputStream) {
        EpubTransformOptions options = EpubTransformOptions.empty();
        if(story.getXtwMetadata() != null)
             options = EpubTransformOptions.fromXtwMetadata(story.getXtwMetadata());
        transform(story, outputStream, options);
    }

    @Override
    public final String getExtension() {
        return EXTENSION;
    }

    /**
     * Transforms a {@link TwStorydata} object to an EPUB file.
     *
     * @param story twine xml to transform, may not be null
     * @param outputStream output stream to write EPUB to, may not be null
     * @param options transform options, contains EPUB metadata, may not me null
     */
    public final void transform(final TwStorydata story,
                                final OutputStream outputStream,
                                final EpubTransformOptions options) {
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
            Resource stylesheetResource = new Resource(
                    ID_AUTO_GEN,
                    story.getStyle().getValue().getBytes(),
                    STORY_STYLESHEET_FILE,
                    MediatypeService.CSS);
            book.getResources().add(stylesheetResource);
        }
        
        // embed all resources
        embedResources(book, story);

        // add all passages
        try {
            for (TwPassagedata passage : story.getTwPassagedata()) {
                // add passage as chapter
                String passageContent = transformPassageTextToXhtml(passage.getName(), passage.getValue());
                Resource passageResource = new Resource(
                        ID_AUTO_GEN,
                        passageContent.getBytes(StandardCharsets.UTF_8),
                        passage.getName() + ".xhtml",
                        MediatypeService.XHTML);

                book.addSection(passage.getName(), passageResource);
            }
        } catch (TwineTransformationFailedException e) {
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

    /**
     * Embeds the resources for each passage.
     *
     * @param book book to embed resources into
     * @param story story to embed resources for
     */
    private void embedResources(final Book book, final TwStorydata story) {
        for(TwPassagedata passage : story.getTwPassagedata()) {
            com.vladsch.flexmark.util.ast.Node rootNode = pdProcessor.parseMarkdown(passage.getValue());
            resourceEmbedder.embed(book, rootNode);
        }
    }

    /**
     * Transforms the text of a passage to a valid XHTML document.
     *
     * @param name name of the passage
     * @param passageText markdown text from a passage
     * @return a valid xhtml document containing the passage text in the body
     * @throws TwineTransformationFailedException if markdown can't be transformed to xhtml
     */
    private String transformPassageTextToXhtml(final String name, final String passageText) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final DocumentBuilderFactory dbf = SafeXmlFactory.InternalOnlyDocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            final DocumentBuilder builder = dbf.newDocumentBuilder();

            // convert markdown to xhtml
            final String passageContent = pdProcessor.markdownToHtml(passageText);
            final String bodyString = String.format("<body>%s</body>", passageContent);
            final Document passageDocument = builder.parse(new InputSource(new StringReader(bodyString)));

            // create html document
            final Document htmlDocument = builder.getDOMImplementation()
                    .createDocument("http://www.w3.org/1999/xhtml", "html", null);

            // create head element
            final Element headElement = htmlDocument.createElement("head");
            htmlDocument.getDocumentElement().appendChild(headElement);

            // add title
            final Element titleElement = htmlDocument.createElement("title");
            titleElement.setTextContent(name);
            headElement.appendChild(titleElement);

            // add stylesheet
            final Element styleElement = htmlDocument.createElement("link");
            styleElement.setAttribute(ATTR_TYPE, "text/css");
            styleElement.setAttribute(ATTR_REL, "stylesheet");
            styleElement.setAttribute(ATTR_HREF, STORY_STYLESHEET_FILE);
            headElement.appendChild(styleElement);

            // add passage content to document
            final Node bodyNode = htmlDocument.importNode(passageDocument.getDocumentElement(), true);
            htmlDocument.getDocumentElement().appendChild(bodyNode);

            // create body element
            final Element bodyElement = (Element) bodyNode;
            bodyElement.setAttribute(ATTR_CLASS, bodyElement.getAttribute(ATTR_CLASS) + " ttp");
            htmlDocument.getDocumentElement().appendChild(bodyNode);

            // transform xml
            final TransformerFactory transformerFactory = SafeXmlFactory.InternalOnlyTransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            final javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();

            final DOMSource source = new DOMSource(htmlDocument);
            final StreamResult result = new StreamResult(out);

            transformer.transform(source, result);

            // set return value
            return out.toString();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new TwineTransformationFailedException(String.format("Could not transform passage '%s'.", name), e);
        }
    }
}
