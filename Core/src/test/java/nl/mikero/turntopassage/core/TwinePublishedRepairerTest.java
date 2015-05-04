package nl.mikero.turntopassage.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

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
        try(InputStream inputStream = getClass().getResourceAsStream("/html/harlowe.html")) {
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
        try(InputStream inputStream = getClass().getResourceAsStream("/html/snowman.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
        validate(outputStream);

        // If we reach this point, the returned XML is valid. Yay!
        Assert.assertTrue(true);
        outputStream.close();
    }

    @Test
    public void repair_SubarQubeStoryFormat_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/sugarcube.html")) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
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

}
