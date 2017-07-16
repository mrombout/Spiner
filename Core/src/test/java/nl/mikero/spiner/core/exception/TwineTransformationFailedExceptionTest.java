package nl.mikero.spiner.core.exception;

import org.junit.Test;

import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwineTransformationFailedExceptionTest {

    @Test
    public void constructor_WithCause_CauseIsProperlySet() {
        // Arrange
        TransformerException e = mock(TransformerException.class);

        // Act
        TwineTransformationFailedException exception = new TwineTransformationFailedException(e);

        // Assert
        assertEquals(e, exception.getCause());
    }

}
