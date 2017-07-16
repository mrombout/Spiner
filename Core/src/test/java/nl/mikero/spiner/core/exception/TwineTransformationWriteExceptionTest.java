package nl.mikero.spiner.core.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwineTransformationWriteExceptionTest {

    @Test
    public void constructor_WithCause_CauseIsProperlySet() {
        // Arrange
        Exception e = mock(Exception.class);

        // Act
        TwineTransformationWriteException exception = new TwineTransformationWriteException(e);

        // Assert
        assertEquals(e, exception.getCause());
    }

}
