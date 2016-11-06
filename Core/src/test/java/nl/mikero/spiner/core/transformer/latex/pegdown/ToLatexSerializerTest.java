package nl.mikero.spiner.core.transformer.latex.pegdown;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.*;

import static org.junit.Assert.*;

public class ToLatexSerializerTest {
    private PegDownProcessor pegDownProcessor;
    private ToLatexSerializer serializer;

    @Before
    public void setUp() {
        pegDownProcessor = new PegDownProcessor();
        serializer = new ToLatexSerializer(new LinkRenderer());
    }

    @Test
    public void toLatex_PlainText_ReturnsSameText() {
        // Arrange
        String markdown = "This is just plain text.";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals(markdown, result);
    }

    @Test
    public void toLatex_Bold_TextBf() {
        // Arrange
        String markdown = "**bold**";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\textbf{bold}", result);
    }

    @Test
    public void toLatex_Italic_Emph() {
        // Arrange
        String markdown = "*italic*";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\emph{italic}", result);
    }

    @Test
    @Ignore("Not parsed correctly, markdown formatting wrong?")
    public void toLatex_BlockQuote_DisplayQuote() {
        // Arrange
        String markdown = "\n> The first quote line\n" +
                "> And the second one\n";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{displayquote}\n  A single line quote\n\\end{displayquote}", result);
    }

    @Test
    public void toLatex_BulletListNode_Enumerate() {
        // Arrange
        String markdown = "* Milk\n" +
                "* Cookies\n" +
                "* Nintendo Switch";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{enumerate}\n  \\item{Milk}\n  \\item{Cookies}\n  \\item{Nintendo Switch}\n\\end{enumerate}", result);
    }

    @Test
    @Ignore("Not parsed correctly, markdown formatting wrong?")
    public void toLatex_DefinitionListNode_Enumerate() {
        // Arrange
        String markdown = "Definition\n" +
                ": And then some definition\n" +
                "Another definition\n" +
                ": And then some more";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{description}\n  \\item{And then some definition}\n  \\item{And then some more}\n\\end{description}", result);
    }
}
