package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LatexWriterTest {
    private LatexWriter writer;

    @Before
    public void setUp() {
        writer = new LatexWriter();
    }

    @Test(expected = NullPointerException.class)
    public void write_NullDocument_ThrowsNullPointerException() throws IOException {
        // Arrange
        OutputStream mockOutputStream = Mockito.mock(OutputStream.class);

        // Act
        writer.write(null, mockOutputStream);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void write_NullOutputStream_ThrowsNullPointerException() throws IOException {
        // Arrange
        LatexDocument document = Mockito.mock(LatexDocument.class);

        // Act
        writer.write(document, null);

        // Assert
    }

    @Test
    public void write_Default_WriteDocumentToOutputStream() throws IOException {
        // Arrange
        String expectedString = "Foo";

        LatexDocument mockDocument = Mockito.mock(LatexDocument.class);
        Mockito.when(mockDocument.toString()).thenReturn(expectedString);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        writer.write(mockDocument, outputStream);

        // Assert
        Assert.assertEquals(expectedString, outputStream.toString());
    }
}
