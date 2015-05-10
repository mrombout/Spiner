package nl.mikero.turntopassage.core.embedder;

import org.junit.Before;
import org.junit.Test;
import org.pegdown.ast.ExpImageNode;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.security.MessageDigest;

public class HashEmbedderFactoryTest {

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
        ExpImageNode node = new ExpImageNode("title", "url", null);

        // Act
        Embedder result = factory.get(node);
        Embedder result2 = factory.get(node);

        // Assert
        assertNotNull(result);
        assertSame(result, result2);
    }

}
