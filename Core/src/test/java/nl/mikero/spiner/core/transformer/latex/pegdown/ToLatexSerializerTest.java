package nl.mikero.spiner.core.transformer.latex.pegdown;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ToLatexSerializerTest {
    private PegDownProcessor pegDownProcessor;
    private LatexPrinter printer;
    private ToLatexSerializer serializer;

    private LinkRenderer mockLinkRenderer;

    @Before
    public void setUp() {
        pegDownProcessor = new PegDownProcessor(Extensions.WIKILINKS | Extensions.QUOTES | Extensions.TASKLISTITEMS | Extensions.DEFINITIONS);
        printer = new LatexPrinter();
        mockLinkRenderer = Mockito.mock(LinkRenderer.class);
        serializer = new ToLatexSerializer(mockLinkRenderer, printer);
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
    public void toLatex_BlockQuote_DisplayQuote() {
        // Arrange
        String markdown = "\n> The first quote line\n" +
                "> And the second one\n";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{displayquote}\n  The first quote line And the second one\n\\end{displayquote}", result);
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
    public void toLatex_DefinitionListNode_Enumerate() {
        // Arrange
        String markdown = "Definition\n" +
                ": And then some definition\n\n" +
                "Another definition\n" +
                ": And then some more";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{description}\n  \\item[Definition] And then some definition\n  \\item[Another definition] And then some more\n\\end{description}", result);
    }

    @Test
    @Ignore("Not implemented yet")
    public void toLatex_TaskListItems_GbOptions() {
        // Arrange
        String markdown = "* [ ] [[Link1]]\n" +
                "* [ ] [[Label2|Link2]]";
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        // Act
        String result = serializer.toLatex(rootNode);

        // Assert
        assertEquals("\\begin{gboptions}\n  \\gboption{Link1}{Link1}\n  \\gboption{Label2}{Link2}\n\\end{gboptions}", result);
    }

    @Test
    public void visit_AbbreviationNode_NothingIsPrinted() {
        // Arrange
        AbbreviationNode node = new AbbreviationNode(new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("", printer.getString());
    }

    @Test
    public void visit_AnchorLinkNode_Unknown() {
        // Arrange
        AnchorLinkNode node = new AnchorLinkNode("Foo");
        Mockito.when(mockLinkRenderer.render(ArgumentMatchers.eq(node)))
                .thenReturn(new LinkRenderer.Rendering("HREF", "TEXT"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\gbturn{TEXT}", printer.getString());
    }

    @Test
    public void visit_AutoLinkNode_Unknown() {
        // Arrange
        AutoLinkNode node = new AutoLinkNode("Foo");

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\url{Foo}", printer.getString());
    }

    @Test
    public void visit_CodeNode_Unknown() {
        // Arrange
        CodeNode node = new CodeNode("Foo");

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\lstinline{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel1_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(1, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\chapter{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel2_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(2, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\section{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel3_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(3, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\subsection{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel4_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(4, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\subsubsection{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel5_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(5, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\paragraph{Foo}", printer.getString());
    }

    @Test
    public void visit_HeaderNodeLevel6_Unknown() {
        // Arrange
        HeaderNode node = new HeaderNode(6, new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\subparagraph{Foo}", printer.getString());
    }

    @Test(expected = IllegalStateException.class)
    public void visit_InvalidHeaderNodeLevel7_ThrowsIllegalStateException() {
        // Arrange
        HeaderNode node = new HeaderNode(7, new TextNode("H7"));

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test
    public void visit_InlineHtmlNode_Unknown() {
        // Arrange
        InlineHtmlNode node = new InlineHtmlNode("Foo");

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("Foo", printer.getString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_ExpImageNode_ThrownUnsupportedOperationException() {
        // Arrange
        ExpImageNode node = new ExpImageNode("Foo", "Bar", new TextNode("Widget"));

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test
    public void visit_ExpLinkNode_ThrownUnsupportedOperationException() {
        // Arrange
        ExpLinkNode node = new ExpLinkNode("Foo", "Bar", new TextNode("Widget"));
        Mockito.when(mockLinkRenderer.render(ArgumentMatchers.eq(node), ArgumentMatchers.any()))
                .thenReturn(new LinkRenderer.Rendering("HREF", "TEXT"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\gbturn{TEXT}", printer.getString());
    }

    @Test
    public void visit_HtmlBlockNodeContainingHTML_NothingIsPrinted() {
        // Arrange
        HtmlBlockNode node = new HtmlBlockNode("<b>Foo</b>");

        // Act
        serializer.visit(node);

        // Assert
        assertEquals(0, printer.getString().length());
    }

    @Test
    public void visit_HtmlBlockNodeContainingPlainText_NothingIsPrinted() {
        // Arrange
        HtmlBlockNode node = new HtmlBlockNode("Foo");

        // Act
        serializer.visit(node);

        // Assert
        assertEquals(0, printer.getString().length());
    }

    @Test
    public void visit_MailLinkNode_Unknown() {
        // Arrange
        MailLinkNode node = new MailLinkNode("foo@bar.com");
        Mockito.when(mockLinkRenderer.render(ArgumentMatchers.eq(node)))
                .thenReturn(new LinkRenderer.Rendering("foo@bar.com", "foo@bar.com"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\gbturn{foo@bar.com}", printer.getString());
    }

    @Test
    public void visit_QuotedNodeDoubleAngle_ReplacedWithGuillemot() {
        // Arrange
        QuotedNode node = new QuotedNode(QuotedNode.Type.DoubleAngle);
        node.getChildren().add(new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\guillemotleft{}Foo\\guillemotright{}", printer.getString());
    }

    @Test
    public void visit_QuotedNodeDouble_ReplacedWithGuillemot() {
        // Arrange
        QuotedNode node = new QuotedNode(QuotedNode.Type.Double);
        node.getChildren().add(new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("‘‘Foo’’", printer.getString());
    }

    @Test
    public void visit_QuotedNodeSingle_ReplacedWithGuillemot() {
        // Arrange
        QuotedNode node = new QuotedNode(QuotedNode.Type.Single);
        node.getChildren().add(new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("‘Foo’", printer.getString());
    }

    @Test
    public void visit_ReferenceNode_NothingIsPrinted() {
        // Arrange
        ReferenceNode node = new ReferenceNode(new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("", printer.getString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_RefImageNode_ThrowsUnsupportedOperationException() {
        // Arrange
        ParaNode paraNode = new ParaNode(new TextNode("Bar"));
        RefImageNode node = new RefImageNode(paraNode, "SEP", new TextNode("Foo"));

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test
    public void visit_SimpleNodeApostrophe_PrintsApostrophe() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Apostrophe);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("’", printer.getString());
    }

    @Test
    public void visit_SimpleNodeEllipsis_PrintsEllipsis() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Ellipsis);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\ldots", printer.getString());
    }

    @Test
    public void visit_SimpleNodeEmdash_PrintsEmdash() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Emdash);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("---", printer.getString());
    }

    @Test
    public void visit_SimpleNodeEndash_PrintsEndash() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Endash);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("--", printer.getString());
    }

    @Test
    public void visit_SimpleNodeHRule_PrintsHRule() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.HRule);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\rule{0.5\\textwidth}{.4pt}", printer.getString());
    }

    @Test
    public void visit_SimpleNodeLinebreak_PrintsLinebreak() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Linebreak);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\linebreak", printer.getString());
    }

    @Test
    public void visit_SimpleNodeNbsp_PrintsNbsp() {
        // Arrange
        SimpleNode node = new SimpleNode(SimpleNode.Type.Nbsp);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\~", printer.getString());
    }

    @Test
    public void visit_StrikeNodeSingleChild_PrintsStrikeNode() {
        // Arrange
        List<Node> childNodes = new ArrayList<>();
        childNodes.add(new TextNode("Foo"));

        StrikeNode node = new StrikeNode(childNodes);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\sout{Foo}", printer.getString());
    }

    @Test
    public void visit_StrikeNodeTwoChildren_PrintsStrikeNode() {
        // Arrange
        List<Node> childNodes = new ArrayList<>();
        childNodes.add(new TextNode("Foo"));
        childNodes.add(new TextNode("Bar"));

        StrikeNode node = new StrikeNode(childNodes);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals("\\sout{FooBar}", printer.getString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableBodyNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableBodyNode node = Mockito.mock(TableBodyNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableCaptionNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableCaptionNode node = Mockito.mock(TableCaptionNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableCellNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableCellNode node = Mockito.mock(TableCellNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableColumnNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableColumnNode node = Mockito.mock(TableColumnNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableHeaderNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableHeaderNode node = Mockito.mock(TableHeaderNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableNode node = Mockito.mock(TableNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void visit_TableRowNode_ThrowsUnsupportedOperationException() {
        // Arrange
        TableRowNode node = Mockito.mock(TableRowNode.class);

        // Act
        serializer.visit(node);

        // Assert
    }

    @Test
    public void visit_Node_NothingIsPrinted() {
        // Arrange
        Node node = Mockito.mock(Node.class);

        // Act
        serializer.visit(node);

        // Assert
        assertEquals(0, printer.getString().length());
    }
}
