package nl.mikero.spiner.core.transformer.epub.embedder;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.service.MediatypeService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.*;

public class ImageEmbedderTest {

    @SuppressWarnings("FieldCanBeLocal")
    private MessageDigest digest;
    private ImageEmbedder embedder;

    @Before
    public void setUp() throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance("SHA-256");
        this.embedder = new ImageEmbedder(digest);
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullBook_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        embedder.embed(null, new URL("file:/img/doesnotexist.png"));

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullUrl_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        embedder.embed(null, new URL("file:/img/doesnotexist.png"));

        // Assert
    }

    @Test
    public void embed_EmbedPngGifJpgSvg_ResourcesAreEmbedded() throws Exception {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, getClass().getResource("/embed/image.jpg").toURI().toURL());
        embedder.embed(book, getClass().getResource("/embed/image.jpeg").toURI().toURL());
        embedder.embed(book, getClass().getResource("/embed/image.png").toURI().toURL());
        embedder.embed(book, getClass().getResource("/embed/image.gif").toURI().toURL());
        embedder.embed(book, getClass().getResource("/embed/image.svg").toURI().toURL());

        // Assert
        Resources resources = book.getResources();

        List<Resource> jpgs = resources.getResourcesByMediaType(MediatypeService.JPG);
        assertResources(jpgs, 2);

        List<Resource> pngs = resources.getResourcesByMediaType(MediatypeService.PNG);
        assertResources(pngs, 1);

        List<Resource> gifs = resources.getResourcesByMediaType(MediatypeService.GIF);
        assertResources(gifs, 1);

        List<Resource> svgs = resources.getResourcesByMediaType(MediatypeService.SVG);
        assertResources(svgs, 1);
    }

    private void assertResources(List<Resource> resources, int numExpected) throws Exception {
        assertEquals(numExpected, resources.size());
        for(Resource resource : resources) {
            assertTrue(resource.getHref().startsWith("Images/"));
            assertTrue(resource.getData().length > 0);
        }
    }

    @Test
    public void embed_EmbedUnsupportedMediaType_ResourceIsEmbeddedAnyway() throws Exception {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, getClass().getResource("/embed/image.bmp").toURI().toURL());

        // Assert
        assertEquals(1, book.getResources().size());
    }

    @Test(expected = IOException.class)
    public void embed_UrlDoesNotExist_ThrowsException() throws Exception {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, new URL("file:/does/not/exist"));

        // Assert
    }

    @Test
    public void embed_ValidResource_HrefAndEmbedHashAreEqual() throws Exception {
        // Arrange
        Book book = new Book();
        URL url = getClass().getResource("/embed/image.png").toURI().toURL();

        // Act

        embedder.embed(book, url);
        String result = embedder.getHref(url);

        // Assert
        List<Resource> pngs = book.getResources().getResourcesByMediaType(MediatypeService.PNG);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result, pngs.get(0).getHref());
    }

}
