package nl.mikero.spiner.core.service;

import nl.mikero.spiner.core.transformer.epub.EpubTransformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwineRepairer;
import nl.mikero.spiner.core.exception.TwineValidationFailedException;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EpubTransformerTest {

    private TwineRepairer mockPublishedRepairer;
    private TwineRepairer mockArchiveRepairer;
    private TwineStoryEpubTransformer mockTwineStoryEpubTransformer;
    private TwineArchiveParser mockTwineArchiveParser;
    private ExtendTwineXmlTransformer mockExendTwineXmlTransformer;

    private EpubTransformer epubTransformer;

    @Before
    public void setUp() {
        mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/valid.xml"));
        mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/valid.xml"));
        mockTwineStoryEpubTransformer = mock(TwineStoryEpubTransformer.class);
        mockTwineArchiveParser = mock(TwineArchiveParser.class);
        mockExendTwineXmlTransformer = mock(ExtendTwineXmlTransformer.class);

        this.epubTransformer = new EpubTransformer(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);
    }

    @Test(expected = NullPointerException.class)
    public void validate_NullInput_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        epubTransformer.validate(null);

        // Assert
    }

    @Test(expected = TwineValidationFailedException.class)
    public void validate_InvalidXml_ThrowsTwineValidationFailedException() throws Exception {
        // Arrange

        // Act
        try(InputStream in = getClass().getResourceAsStream("/xml/invalid.xml")) {
            epubTransformer.validate(in);
        }

        // Assert
    }

    @Test
    public void validate_ValidXml_ReturnsTrue() throws Exception {
        // Arrange

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/xml/valid.xml")) {
            result = epubTransformer.validate(in);
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
            result = epubTransformer.isArchive(in);
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void isArchive_PublishedStoryInput_ReturnsFalse() throws Exception {
        // Arrange
        TwineRepairer mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));
        TwineRepairer mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));

        EpubTransformer epubTransformer = new EpubTransformer(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/harlowe.html")) {
            result = epubTransformer.isArchive(in);
        }

        // Assert
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void isArchive_NullInput_ThrowsNullPointerException() {
        // Arrange

        // Act
        boolean result = epubTransformer.isArchive(null);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isPublished_ArchiveInput_ReturnsTrue() throws Exception {
        // Arrange

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/valid-archive.html")) {
            result = epubTransformer.isPublished(in);
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void isPublished_PublishedStoryInput_ReturnsFalse() throws Exception {
        // Arrange
        TwineRepairer mockPublishedRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));
        TwineRepairer mockArchiveRepairer = new TwineRepairerMock(getClass().getResourceAsStream("/xml/invalid.xml"));

        EpubTransformer epubTransformer = new EpubTransformer(mockPublishedRepairer, mockArchiveRepairer, mockTwineStoryEpubTransformer, mockTwineArchiveParser, mockExendTwineXmlTransformer);

        // Act
        boolean result;
        try(InputStream in = getClass().getResourceAsStream("/html/harlowe.html")) {
            result = epubTransformer.isPublished(in);
        }

        // Assert
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void isPublished_NullInput_ThrowsNullPointerException() {
        // Arrange

        // Act
        boolean result = epubTransformer.isPublished(null);

        // Assert
    }

}
