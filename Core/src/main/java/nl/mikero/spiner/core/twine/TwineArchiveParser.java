package nl.mikero.spiner.core.twine;

import nl.mikero.spiner.core.exception.TwineParseFailedException;
import nl.mikero.spiner.core.twine.model.ObjectFactory;
import nl.mikero.spiner.core.twine.model.TwStoriesdata;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Parses a Twine Archive as exported from Twine 2.
 */
public class TwineArchiveParser {

    private static final List<String> IGNORED_ERRORS = Collections.singletonList("cvc-complex-type.3.2.2");

    /**
     * Parses a well-formed and valid Twine Archive XML file into a
     * {@link TwStoriesdata} tree.
     *
     * It is somewhat lenient in regards to XML parsing and will ignore the following errors:
     *
     *  - cvc-complex-type.3.2.2
     *
     * @see https://github.com/apache/xerces2-j/blob/e5a239b96fd2cff6566a29e7a4a3a4a2bbf9b0d4/src/org/apache/xerces/impl/msg/XMLSchemaMessages.properties
     * @param inputStream input stream to read XML file from
     * @return unmarshalled xml object tree
     * @throws TwineParseFailedException when input could not be parsed
     */
    public TwStoriesdata parse(final InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(getClass().getResource("/format.xsd"));
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(event -> IGNORED_ERRORS.stream().anyMatch(ignoredError -> event.getMessage().startsWith(ignoredError)));

            JAXBElement<TwStoriesdata> unmarshalledObject =
                    (JAXBElement<TwStoriesdata>) unmarshaller.unmarshal(inputStream);
            return unmarshalledObject.getValue();
        } catch (SAXException | JAXBException e) {
            throw new TwineParseFailedException(e);
        }
    }

}
