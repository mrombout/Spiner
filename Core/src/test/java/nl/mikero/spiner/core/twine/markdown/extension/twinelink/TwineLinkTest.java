package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import org.junit.Assert;
import org.junit.Test;

public class TwineLinkTest {
    @Test
    public void getText_ForwardLinkBasedSequence_ReturnsCorrectTextAndPassage() {
        // Arrange
        CharSubSequence sequence = CharSubSequence.of("[[my text->my_passage]]");
        TwineLink twineLink = new TwineLink(sequence);

        // Act
        BasedSequence text = twineLink.getText();
        BasedSequence passage = twineLink.getPassage();

        // Assert
        Assert.assertEquals("my text", text.unescape());
        Assert.assertEquals("my_passage", passage.unescape());
    }

    @Test
    public void getText_BackwardLinkBasedSequence_ReturnsCorrectTextAndPassage() {
        // Arrange
        CharSubSequence sequence = CharSubSequence.of("[[my_passage<-my text]]");
        TwineLink twineLink = new TwineLink(sequence);

        // Act
        BasedSequence text = twineLink.getText();
        BasedSequence passage = twineLink.getPassage();

        // Assert
        Assert.assertEquals("my text", text.unescape());
        Assert.assertEquals("my_passage", passage.unescape());
    }

    @Test
    public void getText_Twine1LinkBasedSequence_ReturnsCorrectTextAndPassage() {
        // Arrange
        CharSubSequence sequence = CharSubSequence.of("[[my text|my_passage]]");
        TwineLink twineLink = new TwineLink(sequence);

        // Act
        BasedSequence text = twineLink.getText();
        BasedSequence passage = twineLink.getPassage();

        // Assert
        Assert.assertEquals("my text", text.unescape());
        Assert.assertEquals("my_passage", passage.unescape());
    }

    @Test
    public void getText_PlainLinkBasedSequence_ReturnsCorrectTextAndPassage() {
        // Arrange
        CharSubSequence sequence = CharSubSequence.of("[[my text]]");
        TwineLink twineLink = new TwineLink(sequence);

        // Act
        BasedSequence text = twineLink.getText();
        BasedSequence passage = twineLink.getPassage();

        // Assert
        Assert.assertEquals("my text", text.unescape());
        Assert.assertEquals("my text", passage.unescape());
    }
}
