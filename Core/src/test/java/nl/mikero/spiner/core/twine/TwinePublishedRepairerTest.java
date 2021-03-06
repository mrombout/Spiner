package nl.mikero.spiner.core.twine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TwinePublishedRepairerTest extends TwineRepairerTest {

    private TwinePublishedRepairer repairer;

    @Before
    public void setUp() {
        this.repairer = new TwinePublishedRepairer();
    }

    @Test
    public void repair_HarloweStoryFormat_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/harlowe_2.1.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test
    public void repair_SnowmanStoryFormat_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/snowman_1.3.0.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test
    public void repair_SpecialCharactersInTitle_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/special-character-title.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test
    public void repair_SugarQubeStoryFormat_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/sugarcube_2.21.0.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test
    public void repair_StyledStoryFormat_StyleIsCopied() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/harlowe_2.1.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        try(InputStream in = new ByteArrayInputStream(outputStream.toByteArray())) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(in);

            Assert.assertNotNull(document.getElementsByTagName("style"));
            Node style = document.getElementsByTagName("style").item(0);
            Assert.assertEquals("\n* { background-color: #ffffff; }\n    ", style.getTextContent());
        }

        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test(expected = NullPointerException.class)
    public void repair_InputNull_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        try(OutputStream outputStream = new ByteArrayOutputStream()) {
            repairer.repair(null, outputStream);
        }

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void repair_OutputNull_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/harlowe.html")) {
            repairer.repair(inputStream, null);
        }

        // Assert
    }

    @Test(expected = IOException.class)
    public void repair_InputDoesNotExist_ThrowsIOException() throws Exception {
        // Arrange
        File doesNotExist = new File("/html/doesNotExist.xml");
        OutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = new FileInputStream(doesNotExist)) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
    }

    @Test(expected = TwineRepairFailedException.class)
    public void repair_NoTwStoryData_ThrowsTwineRepairFailedException() throws Exception {
        // Arrange


        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/without-storydata.html");
            OutputStream outputStream = new ByteArrayOutputStream()) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert

    }

    @Test(expected = TwineRepairFailedException.class)
    public void repair_ReadingFromInputStreamFails_ThrowsTwineRepairFailedException() {
        // Arrange
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        OutputStream mockOutputStream = Mockito.mock(OutputStream.class);

        // Act
        repairer.repair(mockInputStream, mockOutputStream);

        // Assert
    }

}
