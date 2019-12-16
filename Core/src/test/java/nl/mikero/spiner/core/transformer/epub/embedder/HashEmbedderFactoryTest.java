package nl.mikero.spiner.core.transformer.epub.embedder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import org.pegdown.ast.ExpImageNode;

import java.security.MessageDigest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

@Ignore
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
    public void get_ExpImageNodeParameterTwice_ReturnsSameImageEmbedder() {
        // Arrange
//        ExpImageNode node = new ExpImageNode("title", "url", null);

        // Act
//        Embedder result = factory.get(node);
//        Embedder result2 = factory.get(node);

        // Assert
//        assertNotNull(result);
//        assertSame(result, result2);
    }

}
