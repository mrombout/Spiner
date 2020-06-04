package nl.mikero.spiner.core.transformer.epub.embedder;

import java.io.IOException;
import java.net.URL;

import nl.siegmann.epublib.domain.Book;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HtmlResourceEmbedderTest {

    @SuppressWarnings("FieldCanBeLocal")
    private EmbedderFactory embedderFactory;
    private Embedder mockEmbedder;
    private HtmlResourceEmbedder embedder;

    @Before
    public void setUp() {
        this.embedderFactory = mock(EmbedderFactory.class);
        this.mockEmbedder = mock(Embedder.class);
        this.embedder = new HtmlResourceEmbedder(embedderFactory, Parser.htmlParser());

        when(embedderFactory.get(any(URL.class))).thenReturn(mockEmbedder);
    }

    @Test
    public void embed_ImgTag_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<img src=\"http://google.com\" />";

        // Act
        embedder.embed(book, content);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_ImgTag_SrcIsReplacedWithEmbeddedResourceHref() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<img src=\"http://google.com\" />";

        String embeddedResourceLocation = "Images/some-hash.jpg";
        when(mockEmbedder.getHref(any(URL.class))).thenReturn(embeddedResourceLocation);

        // Act
        String result = embedder.embed(book, content);

        // Assert
        Assert.assertTrue(result.contains(embeddedResourceLocation));
        Assert.assertFalse(result.contains(content));
    }

    @Test
    public void embed_OneNestedExpImageNode_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<p><img src=\"http://google.com\" /></p>";

        // Act
        embedder.embed(book, content);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_MultipleNestedExpImageNodes_EmbedIsCalled() throws IOException {
        // Arrange
        String nestedNode1UrlPath = "http://google.com";
        String nestedNode2UrlPath = "http://bitbucket.com";
        String nestedNode3UrlPath = "http://github.com";

        Book book = new Book();

        org.jsoup.nodes.Document document = new org.jsoup.nodes.Document("");

        Element body = document.appendElement("body");
        Element paragraph = body.appendElement("p");

        Element img1 = paragraph.appendElement("img");
        img1.attr("src", nestedNode1UrlPath);

        Element img2 = paragraph.appendElement("img");
        img2.attr("src", nestedNode2UrlPath);

        Element img3 = paragraph.appendElement("img");
        img3.attr("src", nestedNode3UrlPath);

        // Act
        embedder.embed(book, document.toString());

        // Assert
        URL nestedNode1Url = new URL(nestedNode1UrlPath);
        URL nestedNode2Url = new URL(nestedNode2UrlPath);
        URL nestedNode3Url = new URL(nestedNode3UrlPath);

        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode1Url));
        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode2Url));
        verify(mockEmbedder, times(1)).embed(eq(book), eq(nestedNode3Url));
    }

    @Test
    public void embed_NoEmbeddableNode_EmbedIsNotCalled() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<p><b>Nothing</b> in <i>here</i> is embeddable</p>";

        // Act
        embedder.embed(book, content);

        // Assert
        verify(mockEmbedder, never()).embed(eq(book), any(URL.class));
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullContent_ThrowsNullPointerException() {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullBook_ThrowsNullPointerException() {
        // Arrange
        String content = "<p>Lorum ipsum.</p>";

        // Act
        embedder.embed(null, content);

        // Assert
    }

    @Test
    public void embed_InvalidURL_IgnoresThatResource() throws IOException {
        // Arrange
        Book book = new Book();

        org.jsoup.nodes.Document document = new org.jsoup.nodes.Document("");

        Element body = document.appendElement("body");
        Element paragraph = body.appendElement("p");

        Element img1 = paragraph.appendElement("img");
        img1.attr("src", "hyyp://www.google.");

        Element img2 = paragraph.appendElement("img");
        img2.attr("src", "http://google.com");

        // Act
        embedder.embed(book, document.toString());

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), eq(new URL("http://google.com")));
    }

    @Test
    public void embed_Base64Images_IgnoresThatResource() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<img src=\"data:image/png;base64,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\" />";

        // Act
        embedder.embed(book, content);

        // Assert
        verify(mockEmbedder, never()).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_UnrecognizedProtocol_IgnoresThatResource() throws IOException {
        // Arrange
        Book book = new Book();
        String content = "<img src=\"mmd:some_protocol\" />";

        // Act
        embedder.embed(book, content);

        // Assert
        verify(mockEmbedder, never()).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_AnchorLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        String content = "<p>Hello <a href=\"https://en.wikipedia.org/wiki/Bobby%27s_World\">World!</a>!";

        // Act
        embedder.embed(book, content);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_CodeNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();
        String content = "<code>Code is not an embedded resources.</code>";

        // Act
        embedder.embed(book, content);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }
}
