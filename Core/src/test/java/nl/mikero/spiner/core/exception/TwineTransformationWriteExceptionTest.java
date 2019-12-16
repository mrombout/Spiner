package nl.mikero.spiner.core.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwineTransformationWriteExceptionTest {

    @Test
    public void constructor_WithMessageNoCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";

        // Act
        TwineTransformationWriteException exception = new TwineTransformationWriteException(msg, null);

        // Assert
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void constructor_NoMessageWithCause_MessageIsProperlySet() {
        // Arrange
        Exception e = new Exception();

        // Act
        TwineTransformationWriteException exception = new TwineTransformationWriteException(null, e);

        // Assert
        assertEquals(e, exception.getCause());
    }

    @Test
    public void constructor_WithMessageWithCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";
        Exception e = new Exception();

        // Act
        TwineTransformationWriteException exception = new TwineTransformationWriteException(msg, e);

        // Assert
        assertEquals(msg, exception.getMessage());
        assertEquals(e, exception.getCause());
    }

}
