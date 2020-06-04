package nl.mikero.spiner.core.transformer.epub.embedder;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class HashEmbedderFactoryTest {

    @SuppressWarnings("FieldCanBeLocal")
    private MessageDigest mockDigest;
    private HashEmbedderFactory factory;

    @Before
    public void setUp() {
        this.mockDigest = mock(MessageDigest.class);
        this.factory = new HashEmbedderFactory(mockDigest);
    }

    @Test
    public void get_SameExpImageNodeParameterTwice_ReturnsSameImageEmbedder() throws MalformedURLException {
        // Arrange
        URL url = new URL("http://google.com/img.jpg");

        // Act
        Embedder result = factory.get(url);
        Embedder result2 = factory.get(url);

        // Assert
        assertNotNull(result);
        assertSame(result, result2);
    }

}
