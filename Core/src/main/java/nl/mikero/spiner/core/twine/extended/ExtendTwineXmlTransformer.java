package nl.mikero.spiner.core.twine.extended;

import nl.mikero.spiner.core.exception.ExtendTwineXmlTransformFailedException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Transform a regular Twine XML file to an extended Twine XML format.
 */
public class ExtendTwineXmlTransformer {

    /**
     * Transforms a regular Twine XML file to an extended Twine XML format.
     *
     * @param input regular twine xml input, may not be null
     * @param output extended twine xml output, may not be null
     * @throws ExtendTwineXmlTransformFailedException when transformation fails
     */
    public void transform(InputStream input, OutputStream output) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(input);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            StreamSource stylesheetSource = new StreamSource(getClass().getResourceAsStream("/extend.xsl"));
            Transformer transformer = transformerFactory.newTransformer(stylesheetSource);

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            throw new ExtendTwineXmlTransformFailedException(e);
        }
    }

}
