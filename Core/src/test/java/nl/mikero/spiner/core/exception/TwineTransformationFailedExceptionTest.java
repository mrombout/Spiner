package nl.mikero.spiner.core.exception;

import org.junit.Test;

import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwineTransformationFailedExceptionTest {

    @Test
    public void constructor_WithMessageNoCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";

        // Act
        TwineTransformationFailedException exception = new TwineTransformationFailedException(msg, null);

        // Assert
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void constructor_NoMessageWithCause_MessageIsProperlySet() {
        // Arrange
        TransformerException e = mock(TransformerException.class);

        // Act
        TwineTransformationFailedException exception = new TwineTransformationFailedException(null, e);

        // Assert
        assertEquals(e, exception.getCause());
    }

    @Test
    public void constructor_WithMessageWithCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";
        TransformerException e = mock(TransformerException.class);

        // Act
        TwineTransformationFailedException exception = new TwineTransformationFailedException(msg, e);

        // Assert
        assertEquals(msg, exception.getMessage());
        assertEquals(e, exception.getCause());
    }

}
