package nl.mikero.spiner.core.twine;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.*;

public class TwineArchiveRepairerTest extends TwineRepairerTest {

    private TwineArchiveRepairer repairer;

    @Before
    public void setUp() {
        this.repairer = new TwineArchiveRepairer();
    }

    @Test
    public void repair_ValidInput_ReturnsValidTwineXml() throws Exception {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/valid-archive.html")) {
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
        try(InputStream inputStream = getClass().getResourceAsStream("/html/valid-archive.html")) {
            repairer.repair(inputStream, null);
        }

        // Assert
    }

    @Test(expected = TwineRepairFailedException.class)
    public void repair_OutputInvalid_ThrowsTwineRepairFailedException() throws Exception {
        // Arrange
        final OutputStream os = Mockito.mock(OutputStream.class);
        Mockito.doThrow(IOException.class).when(os).write(ArgumentMatchers.any());

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/html/valid-archive.html")) {
            repairer.repair(inputStream, os);
        }

        // Assert
    }

    @Test(expected = IOException.class)
    public void repair_InputDoesNotExist_ThrowsIOException() throws Exception {
        // Arrange
        File doesNotExist = new File("/xml/doesNotExist.xml");
        OutputStream outputStream = new ByteArrayOutputStream();

        // Act
        try(InputStream inputStream = new FileInputStream(doesNotExist)) {
            repairer.repair(inputStream, outputStream);
        }

        // Assert
    }

}
