package nl.mikero.spiner.core.twine.extended;

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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nl.mikero.spiner.core.exception.ExtendTwineXmlTransformFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static org.junit.Assert.assertEquals;

public class ExtendTwineXmlTransformerTest {

    @SuppressWarnings("FieldCanBeLocal")
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;

    @SuppressWarnings("FieldCanBeLocal")
    private SchemaFactory schemaFactory;
    @SuppressWarnings("FieldCanBeLocal")
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

    @Test
    public void transform_UnexpectedElement_IgnoresUnexpectedElements() throws IOException, SAXException {
        // Arrange
        byte[] result = new byte[0];

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_unexpected_element.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
            result = outputStream.toByteArray();
        }

        // Assert
        try(InputStream in = new ByteArrayInputStream(result)) {
            Document document = documentBuilder.parse(in);
            NodeList xtwMetadatas = document.getElementsByTagName("xtw-metadata");
            assertEquals(1, xtwMetadatas.getLength());

            assertEquals("Title is still set!", xtwMetadatas.item(0).getChildNodes().item(1).getTextContent());
        }
    }

    @Test
    @Ignore("#43 throw exception, or try to repair the document for this scenario?")
    public void transform_MetadataInvalidClosingTag_ThrowsException() {
        // Arrange

        // Act / Assert
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/metadata_not_well_formed.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
        } catch(Throwable e) {
            Assert.assertTrue(String.format("Expected .transform to throw a %s exception, but instead got an %s", ExtendTwineXmlTransformFailedException.class, e), e instanceof ExtendTwineXmlTransformFailedException);
            return;
        }

        Assert.fail("Transform did now throw an exception");
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

    @Test(expected = ExtendTwineXmlTransformFailedException.class)
    public void transform_XmlNotWellFormed_ThrowsException() throws IOException {
        // Arrange

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/not_well_formed.xml"); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            transformer.transform(inputStream, outputStream);
        }

        // Assert
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
