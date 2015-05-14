package nl.mikero.turntopassage.core.service;

import nl.mikero.turntopassage.core.TwineArchiveParser;
import nl.mikero.turntopassage.core.TwineRepairer;
import nl.mikero.turntopassage.core.exception.TwineValidationFailedException;
import nl.mikero.turntopassage.core.transformer.ExtendTwineXmlTransformer;
import nl.mikero.turntopassage.core.transformer.TwineStoryEpubTransformer;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TwineServiceTest {

    private TwineRepairer mockPublishedRepairer;
    private TwineRepairer mockArchiveRepairer;
    private TwineStoryEpubTransformer mockTwineStoryEpubTransformer;
    private TwineArchiveParser mockTwineArchiveParser;
    private ExtendTwineXmlTransformer mockExendTwineXmlTransformer;

    private TwineService twineService;

    @Before
    public void setUp() {
        mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/valid.xml"));
        mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/valid.xml"));
        mockTwineStoryEpubTransformer = mock(TwineStoryEpubTransformer.class);
        mockTwineArchiveParser = mock(TwineArchiveParser.class);
        mockExendTwineXmlTransformer = mock(ExtendTwineXmlTransformer.class);

        this.twineService = new TwineService(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);
    }

    @Test(expected =  NullPointerException.class)
    public void transform_NullInput_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        try(OutputStream out = new ByteArrayOutputStream()) {
            twineService.transform(null, out);
        }

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullOutput_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        try(InputStream in = getClass().getResourceAsStream("/html/harlowe.html")) {
            twineService.transform(in, null);
        }

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void validate_NullInput_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        twineService.validate(null);

        // Assert
    }

    @Test(expected = TwineValidationFailedException.class)
    public void validate_InvalidXml_ThrowsTwineValidationFailedException() throws Exception {
        // Arrange

        // Act
        try(InputStream in = getClass().getResourceAsStream("/xml/invalid.xml")) {
            twineService.validate(in);
        }

        // Assert
    }

    @Test
    public void validate_ValidXml_ReturnsTrue() throws Exception {
        // Arrange

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/xml/valid.xml")) {
            result = twineService.validate(in);
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void isArchive_ArchiveInput_ReturnsTrue() throws Exception {
        // Arrange

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/valid-archive.html")) {
            result = twineService.isArchive(in);
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void isArchive_PublishedStoryInput_ReturnsFalse() throws Exception {
        // Arrange
        TwineRepairer mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));
        TwineRepairer mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));

        TwineService twineService = new TwineService(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/harlowe.html")) {
            result = twineService.isArchive(in);
        }

        // Assert
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void isArchive_NullInput_ThrowsNullPointerException() {
        // Arrange

        // Act
        boolean result = twineService.isArchive(null);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isPublished_ArchiveInput_ReturnsTrue() throws Exception {
        // Arrange

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/valid-archive.html")) {
            result = twineService.isPublished(in);
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void isPublished_PublishedStoryInput_ReturnsFalse() throws Exception {
        // Arrange
        TwineRepairer mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));
        TwineRepairer mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));

        TwineService twineService = new TwineService(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/harlowe.html")) {
            result = twineService.isPublished(in);
        }

        // Assert
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void isPublished_NullInput_ThrowsNullPointerException() {
        // Arrange

        // Act
        boolean result = twineService.isPublished(null);

        // Assert
    }

}
