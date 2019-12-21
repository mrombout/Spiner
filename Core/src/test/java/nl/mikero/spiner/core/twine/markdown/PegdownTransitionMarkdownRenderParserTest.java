package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.util.ast.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PegdownTransitionMarkdownRenderParserTest {
    private PegdownTransitionMarkdownRenderParser parser;

    @Before
    public void setUp() {
        parser = new PegdownTransitionMarkdownRenderParser();
    }

    @Test
    public void markdownToHtml_ConvertsMarkdownToHTML() {
        // Arrange

        // Act
        String result = parser.markdownToHtml("# Hello\n\n*M*arkdown!");

        // Assert
        Assert.assertEquals("<h1>Hello</h1>\n<p><em>M</em>arkdown!</p>\n", result);
    }

    @Test
    public void parseMarkdown() {
        // Arrange

        // Act
        Node result = parser.parseMarkdown("# Hello\n\n*M*arkdown!");

        // Assert
        Assert.assertTrue("Expected first element to be a header", result.getFirstChild() instanceof Heading);
        Assert.assertTrue("Expected last element to be a paragraph", result.getLastChild() instanceof Paragraph);
    }
}
