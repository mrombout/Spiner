package nl.mikero.spiner.core.twine.extended;

import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

import static org.junit.Assert.*;

public class ExtendTwineXmlTransformerTest {

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;

    private SchemaFactory schemaFactory;
    private Schema schema;

    private Validator validator;

    private ExtendTwineXmlTransformer transformer;

    @Before
    public void setUp() throws ParserConfigurationException, SAXException {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = documentBuilderFactory.newDocumentBuilder();

        this.schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        this.schema = schemaFactory.newSchema(getClass().getResource("/format.xsd"));

        this.validator = schema.newValidator();

        this.transformer = new ExtendTwineXmlTransformer();
    }

    @Test
    public void transform_MetadataPassage_ReturnsValidXTwineML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        byte[] result;

        // Act
        try(InputStream in = getClass().getResourceAsStream("/xml/metadata.xml"); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            transformer.transform(in, out);
            result = out.toByteArray();
        }

        // Assert
        try(InputStream in = new ByteArrayInputStream(result)) {
            Source inputSource = new StreamSource(in);
            validator.validate(inputSource);

            in.reset();

            Document document = documentBuilder.parse(in);
            NodeList xtwMetadatas = document.getElementsByTagName("xtw-metadata");
            assertEquals(1, xtwMetadatas.getLength());

            assertEquals("Just a Title!", xtwMetadatas.item(0).getChildNodes().item(1).getTextContent());
        }
    }

    @Test
    public void transform_NoMetadataPassage_ReturnsValidXTwineML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        byte[] result;

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_none.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
            result = outputStream.toByteArray();
        }

        // Assert
        try(InputStream in = new ByteArrayInputStream(result)) {
            Source inputSource = new StreamSource(in);
            validator.validate(inputSource);

            in.reset();

            Document document = documentBuilder.parse(in);
            NodeList xtwMetadatas = document.getElementsByTagName("xtw-metadata");
            assertEquals(0, xtwMetadatas.getLength());
        }
    }

    @Test(expected = SAXParseException.class)
    public void transform_UnexpectedElement_ThrowsSAXParseException() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        byte[] result;

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_invalid_element.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
            result = outputStream.toByteArray();
        }

        // Assert
        assertNotEquals(0, result.length);
        try(InputStream in = new ByteArrayInputStream(result)) {
            Source inputSource = new StreamSource(in);
            validator.validate(inputSource);
        }
    }

    @Test(expected = SAXParseException.class)
    public void transform_MetadataPassageInvalid_ThrowsException() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        byte[] result;

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_invalid_form.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
            result = outputStream.toByteArray();
        }

        // Assert
        assertNotEquals(0, result.length);
        try(InputStream in = new ByteArrayInputStream(result)) {
            Source inputSource = new StreamSource(in);
            validator.validate(inputSource);
        }
    }

    @Test
    public void transform_MetadataPassageEmpty_ThrowsException() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        byte[] result;

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_empty.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
            result = outputStream.toByteArray();
        }

        // Assert
        try(InputStream in = new ByteArrayInputStream(result)) {
            Document document = documentBuilder.parse(in);
        }
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullInput_ThrowsNullPointerException() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        transformer.transform(null, outputStream);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullOutput_ThrowsNullPointerException() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Arrange
        InputStream inputStream = getClass().getResourceAsStream("/xml/metadata.xml");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        transformer.transform(inputStream, null);

        // Assert
    }

}
