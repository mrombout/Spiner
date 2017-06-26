package nl.mikero.spiner.frontend.exception;

import org.junit.Assert;
import org.junit.Test;

public class FxmlLoadFailedExceptionTest {
    @Test(expected = NullPointerException.class)
    public void construct_NullFileName_ThrowsNullPointerException() {
        // Arrange
        Exception cause = new Exception();

        // Act
        new FxmlLoadFailedException(null, cause);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullThrowable_ThrowsNullPointerException() {
        // Arrange

        // Act
        new FxmlLoadFailedException("Foo.txt", null);

        // Assert
    }

    @Test
    public void construct_ProperFileName_ReturnsNiceMessage() {
        // Arrange
        Exception cause = new Exception();

        // Act
        FxmlLoadFailedException exception = new FxmlLoadFailedException("Foo.txt", cause);

        // Assert
        Assert.assertEquals("Could not load 'Foo.txt'.", exception.getMessage());
    }
}
