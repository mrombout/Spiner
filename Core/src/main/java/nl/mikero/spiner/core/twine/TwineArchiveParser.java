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

/**
 * Parses a Twine Archive as exported from Twine 2.
 */
public class TwineArchiveParser {

    /**
     * Parses a well-formed and valid Twine Archive XML file into a
     * {@link TwStoriesdata} tree.
     *
     * @param inputStream input stream to read XML file from
     * @return unmarshalled xml object tree
     * @throws TwineParseFailedException when input could not be parsed
     */
    public TwStoriesdata parse(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(getClass().getResource("/format.xsd"));
            unmarshaller.setSchema(schema);

            JAXBElement<TwStoriesdata> unmarshalledObject = (JAXBElement<TwStoriesdata>) unmarshaller.unmarshal(inputStream);
            return unmarshalledObject.getValue();
        } catch (SAXException | JAXBException e) {
            throw new TwineParseFailedException(e);
        }
    }

}
