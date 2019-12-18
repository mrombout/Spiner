package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwineLinkLinkRefProcessorTest {
    @Test
    public void isMatch_NotMatching_ReturnsFalse() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();
        CharSubSequence nodeChars = CharSubSequence.of("((not a link))");

        // Act
        boolean result = processor.isMatch(nodeChars);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void isMatch_Matching_ReturnsTrue() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();
        CharSubSequence nodeChars = CharSubSequence.of("[[a matching link]]");

        // Act
        boolean result = processor.isMatch(nodeChars);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void adjustInlineText_ReturnsTextIfNotNull() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();
        TwineLink twineLink = new TwineLink(CharSubSequence.of("[[my text->a matching link]]"));

        // Act
        BasedSequence basedSequence = processor.adjustInlineText(null, twineLink);

        // Assert
        Assert.assertEquals("my text", basedSequence.unescape());
    }

    @Test
    public void adjustInlineText_ReturnsPassageIfTextIsNull() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();
        TwineLink twineLink = new TwineLink(CharSubSequence.of("[[a matching link]]"));

        // Act
        BasedSequence basedSequence = processor.adjustInlineText(null, twineLink);

        // Assert
        assertEquals("a matching link", basedSequence.unescape());
    }

    @Test
    public void getBracketNestingLevel() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();

        // Act
        int result = processor.getBracketNestingLevel();

        // Assert
        assertEquals(TwineLinkLinkRefProcessor.BRACKET_NESTING_LEVEL, result);
    }

    @Test
    public void getWantExclamantionPrefix() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();

        // Act
        boolean result = processor.getWantExclamationPrefix();

        // Assert
        assertFalse(result);
    }

    @Test
    public void createNode() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();
        CharSubSequence sequence = CharSubSequence.of("[[a matching link]]");

        // Act
        Node result = processor.createNode(sequence);

        // Assert
        assertTrue(result instanceof TwineLink);
    }

    @Test
    public void allowDelimiters() {
        // Arrange
        TwineLinkLinkRefProcessor processor = new TwineLinkLinkRefProcessor();

        // Act
        boolean result = processor.allowDelimiters(null, null, null);

        // Assert
        assertFalse(result);
    }
}