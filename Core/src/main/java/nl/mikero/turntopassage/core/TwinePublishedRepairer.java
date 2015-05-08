package nl.mikero.turntopassage.core;

import nl.mikero.turntopassage.core.exception.TwineRepairFailedException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Repairs a published Twine HTML story file to a well-formed XML document that
 * TurnToPassage understands.
 */
public class TwinePublishedRepairer implements TwineRepairer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwinePublishedRepairer.class);

    /**
     * Matches the {@code <tw-storydata>} open (along with any arbitrary number
     * of arguments) and close tags and anything in between (including
     * newlines).
     */
    private static final Pattern REGEX_TW_STORYDATA = Pattern.compile("(?s)<tw-storydata ([\\s\\w=\"'\\-.]*)>(.*)<\\/tw-storydata>");

    private static final String ELEM_TW_STORIESDATA = "tw-storiesdata";
    private static final String ELEM_TW_STORYDATA = "tw-storydata";
    private static final String ELEM_TW_PASSAGEDATA = "tw-passagedata";
    private static final String ELEM_STYLE = "style";

    private static final String ATTR_PID = "pid";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TAGS = "tags";
    private static final String ATTR_POSITION = "position";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_ID = "id";
    private static final String ATTR_ROLE = "role";
    private static final String ATTR_STYLE = "style";

    /**
     * Repairs a published Twine HTML story file to a valid XML document that
     * TurnToPassage understands.
     * <p>
     * This class repairs a standard published Twine HTML file and "repairs" it
     * to a well-formed XML document by applying the following steps:
     * </p>
     * <ol>
     * <li>Extract {@code <tw-storydata>} from HTML</li>
     * <li>Build a new XML document</li>
     * <li>Copy all {@code <tw-passagedata>} nodes to new XML document</li>
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
            String input = IOUtils.toString(inputStream);

            Matcher matcher = REGEX_TW_STORYDATA.matcher(input);
            matcher.find();
            String xmlInput = matcher.group();

            try (InputStream in = new ByteArrayInputStream(xmlInput.getBytes(StandardCharsets.UTF_8))) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                // create xml document from input
                Document inputDocument = documentBuilder.parse(in);

                // create new document for output
                Document outputDocument = documentBuilder.newDocument();

                // tw-stories
                Element twStoriesdata = outputDocument.createElement(ELEM_TW_STORIESDATA);
                outputDocument.appendChild(twStoriesdata);

                // tw-storydata
                Element twStorydata = outputDocument.createElement(ELEM_TW_STORYDATA);
                twStoriesdata.appendChild(twStorydata);

                // style
                NodeList styles = inputDocument.getElementsByTagName(ELEM_STYLE);
                for(int i = 0; i < styles.getLength(); i++) {
                    Element originalStyle = (Element) styles.item(i);

                    Element style = outputDocument.createElement(ATTR_STYLE);
                    style.setTextContent(originalStyle.getTextContent());
                    style.setAttribute(ATTR_ROLE, originalStyle.getAttribute(ATTR_ROLE));
                    style.setAttribute(ATTR_ID, originalStyle.getAttribute(ATTR_ID));
                    style.setAttribute(ATTR_TYPE, originalStyle.getAttribute(ATTR_TYPE));

                    twStorydata.appendChild(style);
                }

                // tw-passagedata
                NodeList elementsByTagName = inputDocument.getElementsByTagName(ELEM_TW_PASSAGEDATA);
                for (int i = 0; i < elementsByTagName.getLength(); i++) {
                    Element element = (Element) elementsByTagName.item(i);

                    Element twPassagedata = outputDocument.createElement(ELEM_TW_PASSAGEDATA);
                    twPassagedata.setTextContent(element.getFirstChild().getNodeValue());
                    twPassagedata.setAttribute(ATTR_PID, element.getAttribute(ATTR_PID));
                    twPassagedata.setAttribute(ATTR_NAME, element.getAttribute(ATTR_NAME));
                    twPassagedata.setAttribute(ATTR_TAGS, element.getAttribute(ATTR_TAGS));
                    twPassagedata.setAttribute(ATTR_POSITION, element.getAttribute(ATTR_POSITION));

                    twStorydata.appendChild(twPassagedata);
                }

                // transform to output stream
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                Source source = new DOMSource(outputDocument);
                Result result = new StreamResult(outputStream);

                transformer.transform(source, result);
            }
        } catch (IOException e) {
            throw new TwineRepairFailedException("Could not read Twine story file from input stream", e);
        } catch (SAXException e) {
            throw new TwineRepairFailedException("Could not parse file from input stream", e);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new TwineRepairFailedException("Could not repair file due to parsing error", e);
        }
    }
}
