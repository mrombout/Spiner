package nl.mikero.spiner.core.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwineRepairFailedExceptionTest {

    @Test
    public void constructor_WithMessageNoCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(msg, null);

        // Assert
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void constructor_NoMessageWithCause_MessageIsProperlySet() {
        // Arrange
        Exception e = new Exception();

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(null, e);

        // Assert
        assertEquals(e, exception.getCause());
    }

    @Test
    public void constructor_WithMessageWithCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";
        Exception e = new Exception();

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(msg, e);

        // Assert
        assertEquals(msg, exception.getMessage());
        assertEquals(e, exception.getCause());
    }

}
