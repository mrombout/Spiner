package nl.mikero.turntopassage.core.embedder;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.service.MediatypeService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.*;

public class ImageEmbedderTest {

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
        embedder.embed(null, "/img/doesnotexist.png");

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void embed_NullUrl_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        embedder.embed(null, "/img/doesnotexist.png");

        // Assert
    }

    @Test
    public void embed_EmbedPngGifJpgSvg_ResourcesAreEmbedded() throws Exception {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.jpg").toURI()));
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.jpeg").toURI()));
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.png").toURI()));
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.gif").toURI()));
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.svg").toURI()));

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
        embedder.embed(book, Paths.get(getClass().getResource("/embed/image.bmp").toURI()));

        // Assert
        assertEquals(1, book.getResources().size());
    }

    @Test(expected = IOException.class)
    public void embed_UrlDoesNotExist_ThrowsException() throws Exception {
        // Arrange
        Book book = new Book();

        // Act
        embedder.embed(book, "/does/not/exist");

        // Assert
    }

    @Test
    public void embed_ValidResource_HrefAndEmbedHashAreEqual() throws Exception {
        // Arrange
        Book book = new Book();
        Path path = Paths.get(getClass().getResource("/embed/image.png").toURI());

        // Act

        embedder.embed(book, path);
        String result = embedder.getHref(path.toString());

        // Assert
        List<Resource> pngs = book.getResources().getResourcesByMediaType(MediatypeService.PNG);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result, pngs.get(0).getHref());
    }

}
