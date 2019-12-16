package nl.mikero.spiner.core.twine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import nl.mikero.spiner.core.exception.TwineParseFailedException;
import nl.mikero.spiner.core.twine.model.TwStoriesdata;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TwineArchiveParserTest {

    private TwineArchiveParser parser;

    @Before
    public void setUp() {
        this.parser = new TwineArchiveParser();
    }

    @Test
    public void parse_ValidXmlFile_ReturnsTwStoriesData() throws Exception {
        // Arrange

        // Act
        TwStoriesdata result;
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/valid.xml")) {
            result = parser.parse(inputStream);
        }

        // Assert
        assertNotNull(result);
        assertThat(result.getTwStorydata().size(), is(1));
        assertThat(result.getTwStorydata().get(0).getTwPassagedata().size(), is(2));
    }

    @Test
    public void parse_NewLineInPassage_NewLinesAreRetained() throws Exception {
        // Arrange

        // Act
        TwStoriesdata result;
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/valid.xml")) {
            result = parser.parse(inputStream);
        }

        // Assert
        assertNotNull(result);
        assertThat(result.getTwStorydata().size(), is(1));
        assertTrue(result.getTwStorydata().get(0).getTwPassagedata().get(0).getValue().contains("\n"));
    }

    @Test(expected = TwineParseFailedException.class)
    public void parse_InvalidXmlFile_ThrowsTwineParseFailedException() throws Exception {
        // Arrange

        // Act
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/invalid.xml")) {
            parser.parse(inputStream);
        }

        // Assert
    }

    @Test
    public void parse_ExtraAttributeInXmlFile_IgnoresAttribute() throws Exception {
        // Arrange

        // Act
        TwStoriesdata result;
        try(InputStream inputStream = getClass().getResourceAsStream("/xml/extra_attribute.xml")) {
            result = parser.parse(inputStream);
        }

        // Assert
        assertNotNull(result);
    }

    @Test(expected = IOException.class)
    public void parse_InputDoesNotExist_ThrowsIOException() throws Exception {
        // Arrange
        File doesNotExist = new File("/xml/doesNotExist.xml");

        // Act
        try(InputStream inputStream = new FileInputStream(doesNotExist)) {
            parser.parse(inputStream);
        }

        // Assert
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_NullParameter_ThrowsIllegalArgumentException() {
        // Arrange

        // Act
        parser.parse(null);

        // Assert
    }

}
