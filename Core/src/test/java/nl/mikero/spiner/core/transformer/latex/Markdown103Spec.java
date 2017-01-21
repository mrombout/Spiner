package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.pegdown.ToLatexSerializer;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Ignore
public class Markdown103Spec {
    private PegDownProcessor processor;
    private ToLatexSerializer serializer;

    @Before
    public void setUp() {
        processor = new PegDownProcessor(Extensions.WIKILINKS | Extensions.QUOTES | Extensions.TASKLISTITEMS);
        serializer = new ToLatexSerializer(new LinkRenderer());
    }

    @Test
    public void AmpsAndAngleEncoding() throws IOException {
        test("Markdown103/Amps and angle encoding");
    }

    @Test
    public void AutoLinks() throws IOException {
        test("Markdown103/Auto links");
    }

    @Test
    public void BackslashEscapes() throws IOException {
        test("Markdown103/Backslash escapes");
    }

    @Test
    public void BlockqoteWithCodeBlocks() throws IOException {
        test("Markdown103/Blockquotes with code blocks");
    }

    @Test
    public void CodeBlocks() throws IOException {
        test("Markdown103/Code Blocks");
    }

    @Test
    public void CodeSpans() throws IOException {
        test("Markdown103/Code Spans");
    }

    @Test
    public void HardWrappedParagraphsWithListLikeLines() throws IOException {
        test("Markdown103/Hard-wrapped paragraphs with list-like lines");
    }

    @Test
    public void HorizontalRules() throws IOException {
        test("Markdown103/Horizontal rules");
    }

    @Test
    public void InlineHtmlAdvanced() throws IOException {
        test("Markdown103/Inline HTML (Advanced)");
    }

    @Test
    public void InlineHtmlSimple() throws IOException {
        test("Markdown103/Inline HTML (Simple)");
    }

    @Test
    public void InlineHtmlComments() throws IOException {
        test("Markdown103/Inline HTML comments");
    }

    @Test
    public void LinksInlineStyle() throws IOException {
        test("Markdown103/Links, inline style");
    }

    @Test
    public void LinksReferenceStyle() throws IOException {
        test("Markdown103/Links, reference style");
    }

    @Test
    public void LinksShortcutReferences() throws IOException {
        test("Markdown103/Links, shortcut references");
    }

    @Test
    public void LiteralQuotesInTitles() throws IOException {
        test("Markdown103/Literal quotes in titles");
    }

    @Test
    public void MarkdownDocumentationBasics() throws IOException {
        test("Markdown103/Markdown Documentation - Basics");
    }

    @Test
    public void MarkdownDocumentationSyntax() throws IOException {
        test("Markdown103/Markdown Documentation - Syntax");
    }

    @Test
    public void NestedBlockquotes() throws IOException {
        test("Markdown103/Nested blockquotes");
    }

    @Test
    public void NestedLists() throws IOException {
        test("Markdown103/Nested lists");
    }
    
    @Test
    public void OrderedAndUnorderedLists() throws IOException {
        test("Markdown103/Ordered and unordered lists");
    }

    @Test
    public void StrongAndEmTogether() throws IOException {
        test("Markdown103/Strong and em together");
    }

    @Test
    public void Tabs() throws IOException {
        test("Markdown103/Tabs");
    }

    @Test
    public void Tidyness() throws IOException {
        test("Markdown103/Tidyness");
    }

    private void test(String markdownFile) throws IOException {
        InputStream markdownInput = getClass().getResourceAsStream("/spec/" + markdownFile + ".md");
        InputStream expectedOutputStream = getClass().getResourceAsStream("/spec/" + markdownFile + ".tex");

        String markdown = IOUtils.toString(markdownInput, StandardCharsets.UTF_8);
        String expectedOutput = IOUtils.toString(expectedOutputStream, StandardCharsets.UTF_8);

        IOUtils.closeQuietly(markdownInput);
        IOUtils.closeQuietly(expectedOutputStream);

        RootNode astRoot = processor.parseMarkdown(markdown.toCharArray());

        String actualLatex = serializer.toLatex(astRoot);

        Assert.assertEquals(expectedOutput, normalize(actualLatex));
    }

    private String normalize(String string) {
        return string.replace("\r\n", "\n").replace("\r", "\n");
    }
}
