package nl.mikero.spiner.core.twine;

import org.junit.Assert;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class TwineRepairerTest {
    protected void validate(ByteArrayOutputStream outputStream) throws SAXException, IOException {
        Assert.assertNotEquals(outputStream.size(), 0);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(getClass().getResource("/format.xsd"));

        Validator validator = schema.newValidator();

        Source inputSource = new StreamSource(new ByteArrayInputStream(outputStream.toByteArray()));
        validator.validate(inputSource);
    }
}
