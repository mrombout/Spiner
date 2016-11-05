package nl.mikero.spiner.core.twine;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Repairs a published Twine HTML story file to a well-formed XML document that
 * Spiner understands.
 */
public class TwinePublishedRepairer implements TwineRepairer {
    /**
     * Matches the {@code <tw-storydata>} open (along with any arbitrary number
     * of arguments) and close tags and anything in between (including
     * newlines).
     */
    private static final Pattern REGEX_TW_STORYDATA = Pattern.compile("(?s)<tw-storydata (.*)>(.*)<\\/tw-storydata>");

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

    private final Tidy tidy;

    public TwinePublishedRepairer() {
        this.tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setDocType("auto");
        tidy.setXmlOut(true);
        tidy.setXmlTags(true);

        Properties props = new Properties();
        props.setProperty("new-blocklevel-tags", "tw-storydata");
        props.setProperty("new-pre-tags", "tw-passagedata");
        tidy.getConfiguration().addProps(props);
    }

    /**
     * Repairs a published Twine HTML story file to a valid XML document that
     * Spiner understands.
     * <p>
     * This class repairs a standard published Twine HTML file and "repairs" it
     * to a well-formed XML document by applying the following steps:
     * </p>
     * <ol>
     * <li>Extract {@code <tw-storydata>} from HTML using a regular expression.</li>
     * <li>Wrap input in &lt;tw-storiesdata&gt; tag.</li>
     * <li>Parse HTML with jTidy as XML.</li>
     * <li>Write new tidy XML document to output</li>
     * </ol>
     *
     * @param inputStream  input stream to read from, typically a FileInputStream
     * @param outputStream output stream to write to, typically a FileOutputStream
     * @throws TwineRepairFailedException when the input stream can not be repaired
     */
    @Override
    public void repair(InputStream inputStream, OutputStream outputStream) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);

        try {
            String input = IOUtils.toString(inputStream);

            Matcher matcher = REGEX_TW_STORYDATA.matcher(input);
            if(!matcher.find())
                throw new TwineRepairFailedException("Input does not contain any <tw-storydata> nodes.", null);
            String xmlInput = String.format("<tw-storiesdata>%s</tw-storiesdata>", matcher.group());

            try (InputStream in = new ByteArrayInputStream(xmlInput.getBytes(StandardCharsets.UTF_8))) {
                // tidy input
                tidy.parse(in, outputStream);
            }
        } catch (IOException e) {
            throw new TwineRepairFailedException("Could not read Twine story file from input stream", e);
        } catch (IllegalStateException e) {
            throw new TwineRepairFailedException("Could not repair file due to parsing error", e);
        }
    }
}
