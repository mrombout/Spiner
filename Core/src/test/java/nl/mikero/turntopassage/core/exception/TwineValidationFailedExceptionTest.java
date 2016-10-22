package nl.mikero.turntopassage.core.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwineValidationFailedExceptionTest {

    @Test
    public void constructor_WithMessageNoCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";

        // Act
        TwineValidationFailedException exception = new TwineValidationFailedException(msg, null);

        // Assert
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void constructor_NoMessageWithCause_MessageIsProperlySet() {
        // Arrange
        Exception e = mock(Exception.class);

        // Act
        TwineValidationFailedException exception = new TwineValidationFailedException(null, e);

        // Assert
        assertEquals(e, exception.getCause());
    }

    @Test
    public void constructor_WithMessageWithCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";
        Exception e = mock(Exception.class);

        // Act
        TwineValidationFailedException exception = new TwineValidationFailedException(msg, e);

        // Assert
        assertEquals(msg, exception.getMessage());
        assertEquals(e, exception.getCause());
    }

}
