package nl.mikero.spiner.core.transformer.epub.embedder;

import nl.siegmann.epublib.domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pegdown.ast.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ResourceEmbedderTest {

    private EmbedderFactory embedderFactory;
    private Embedder mockEmbedder;
    private ResourceEmbedder embedder;

    @Before
    public void setUp() {
        this.embedderFactory = mock(EmbedderFactory.class);
        this.mockEmbedder = mock(Embedder.class);
        this.embedder = new ResourceEmbedder(embedderFactory);

        when(embedderFactory.get(any(ExpImageNode.class))).thenReturn(mockEmbedder);
    }

    @Test
    public void embed_DirectExpImageNode_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        RootNode rootNode = new RootNode();
        ExpImageNode node = new ExpImageNode("nodeTitle", "http://google.com", new InlineHtmlNode(""));
        rootNode.getChildren().add(node);

        // Act
        embedder.embed(book, rootNode);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_OneNestedExpImageNode_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        RootNode rootNode = new RootNode();

        ExpImageNode secondLevel = new ExpImageNode("nodeTitle", "http://google.com", new InlineHtmlNode(""));
        ArrayList<Node> childNodes = new ArrayList<>();
        childNodes.add(secondLevel);

        ParaNode firstLevel = new ParaNode(childNodes);
        rootNode.getChildren().add(firstLevel);

        // Act
        embedder.embed(book, rootNode);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_MultipleNestedExpImageNodes_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        RootNode rootNode = new RootNode();

        String nestedNode1UrlPath = "http://google1.com";
        String nestedNode2UrlPath = "http://google2.com";
        String nestedNode3UrlPath = "http://google3.com";
        ExpImageNode nestedNode1 = new ExpImageNode("nodeTitle1", nestedNode1UrlPath, new InlineHtmlNode(""));
        ExpImageNode nestedNode2 = new ExpImageNode("nodeTitle2", nestedNode2UrlPath, new InlineHtmlNode(""));
        ExpImageNode nestedNode3 = new ExpImageNode("nodeTitle3", nestedNode3UrlPath, new InlineHtmlNode(""));
        ArrayList<Node> childNodes = new ArrayList<>();
        childNodes.add(nestedNode1);
        childNodes.add(nestedNode2);
        childNodes.add(nestedNode3);

        ParaNode firstLevel = new ParaNode(childNodes);
        rootNode.getChildren().add(firstLevel);

        // Act
        embedder.embed(book, rootNode);

        // Assert
        URL nestedNode1Url = new URL(nestedNode1UrlPath);
        URL nestedNode2Url = new URL(nestedNode2UrlPath);
        URL nestedNode3Url = new URL(nestedNode3UrlPath);

        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode1Url));
        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode2Url));
        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode3Url));
    }

    @Test
    public void embed_EmbeddableInTableCell_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        RootNode rootNode = new RootNode();

        TableNode tableNode = new TableNode();
        rootNode.getChildren().add(tableNode);

        TableBodyNode tableBodyNode = new TableBodyNode();
        tableNode.getChildren().add(tableBodyNode);

        TableRowNode tableRowNode = new TableRowNode();
        tableBodyNode.getChildren().add(tableRowNode);

        TableColumnNode tableColumnNode = new TableColumnNode();
        tableRowNode.getChildren().add(tableColumnNode);

        TableCellNode tableCellNode = new TableCellNode();
        tableColumnNode.getChildren().add(tableCellNode);

        ExpImageNode expImageNode = new ExpImageNode("nodeTitle", "http://google.com", new InlineHtmlNode(""));
        tableCellNode.getChildren().add(expImageNode);

        // Act
        embedder.embed(book, rootNode);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_NoEmbeddableNode_EmbedIsNotCalled() throws IOException {
        // Arrange
        Book book = new Book();

        RootNode rootNode = new RootNode();

        // Act
        embedder.embed(book, rootNode);

        // Assert
        verify(mockEmbedder, never()).embed(eq(book), any(URL.class));
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullRootNode_ThrowsNullPointerException() {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullBook_ThrowsNullPointerException() {
        // Arrange
        RootNode rootNode = new RootNode();
        ExpImageNode node = new ExpImageNode("nodeTitle", "http://google.com", new InlineHtmlNode(""));
        rootNode.getChildren().add(node);

        // Act
        embedder.embed(null, rootNode);

        // Assert
    }

    @Test
    public void embed_AnchorLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new AnchorLinkNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_AutoLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new AutoLinkNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_CodeNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new CodeNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_HtmlBlockNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new HtmlBlockNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_InlineHtmlNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new InlineHtmlNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_MailLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new MailLinkNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_SimpleNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new SimpleNode(SimpleNode.Type.Apostrophe));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_SpecialTextNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new SpecialTextNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_VerbatimNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new VerbatimNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_WikiLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new WikiLinkNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_TextNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(new TextNode("Foo"));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_Node_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        RootNode rootNode = new RootNode();
        rootNode.getChildren().add(Mockito.mock(Node.class));

        // Act
        embedder.embed(book, rootNode);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }
}
