package nl.mikero.spiner.core.transformer.epub.embedder;

import java.io.IOException;
import java.net.URL;

import com.vladsch.flexmark.ast.AutoLink;
import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.MailLink;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import nl.siegmann.epublib.domain.Book;
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

public class ResourceEmbedderTest {

    @SuppressWarnings("FieldCanBeLocal")
    private EmbedderFactory embedderFactory;
    private Embedder mockEmbedder;
    private ResourceEmbedder embedder;

    @Before
    public void setUp() {
        this.embedderFactory = mock(EmbedderFactory.class);
        this.mockEmbedder = mock(Embedder.class);
        this.embedder = new ResourceEmbedder(embedderFactory);

        when(embedderFactory.get(any(Image.class))).thenReturn(mockEmbedder);
    }

    @Test
    public void embed_DirectExpImageNode_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        Image image = new Image();
        image.setUrl(CharSubSequence.of("http://google.com"));
        document.appendChild(image);

        // Act
        embedder.embed(book, document);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), any(URL.class));
    }

    @Test
    public void embed_OneNestedExpImageNode_EmbedIsCalled() throws IOException {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        Paragraph paragraph = new Paragraph();
        Image image = new Image();
        image.setUrl(CharSubSequence.of("http://google.com"));

        document.appendChild(paragraph);
        paragraph.appendChild(image);

        // Act
        embedder.embed(book, document);

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

        Document document = new Document(null, null);
        Paragraph paragraph = new Paragraph();

        Image image1 = new Image();
        image1.setUrl(CharSubSequence.of(nestedNode1UrlPath));

        Image image2 = new Image();
        image2.setUrl(CharSubSequence.of(nestedNode2UrlPath));

        Image image3 = new Image();
        image3.setUrl(CharSubSequence.of(nestedNode3UrlPath));

        document.appendChild(paragraph);
        paragraph.appendChild(image1);
        paragraph.appendChild(image2);
        paragraph.appendChild(image3);

        // Act
        embedder.embed(book, document);

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

        Document document = new Document(null, null);

        // Act
        embedder.embed(book, document);

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
        Document document = new Document(null, null);
        Image image = new Image();
        document.appendChild(image);

        // Act
        embedder.embed(null, document);

        // Assert
    }

    @Test
    public void embed_InvalidURL_IgnoresThatResource() throws IOException {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        Paragraph paragraph = new Paragraph();

        Image invalidImage = new Image();
        invalidImage.setUrl(CharSubSequence.of("hyyp://www.google."));

        Image validImage = new Image();
        validImage.setUrl(CharSubSequence.of("http://google.com"));

        document.appendChild(paragraph);
        paragraph.appendChild(invalidImage);
        paragraph.appendChild(validImage);

        // Act
        embedder.embed(book, document);

        // Assert
        verify(mockEmbedder, times(1)).embed(eq(book), eq(new URL("http://google.com")));
    }

    @Test
    public void embed_AnchorLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        AnchorLink anchorLink = new AnchorLink();
        document.appendChild(anchorLink);

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_AutoLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        AutoLink autoLink = new AutoLink();
        document.appendChild(autoLink);

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_CodeNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        Code code = new Code();
        document.appendChild(code);

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_HtmlBlockNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        HtmlBlock htmlBlock = new HtmlBlock();

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_MailLinkNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        MailLink mailLink = new MailLink();
        document.appendChild(mailLink);

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }

    @Test
    public void embed_TextNode_NoResourceEmbedded() {
        // Arrange
        Book book = new Book();

        Document document = new Document(null, null);
        Text text = new Text();
        document.appendChild(text);

        // Act
        embedder.embed(book, document);

        // Assert
        assertTrue(book.getResources().isEmpty());
    }
}
