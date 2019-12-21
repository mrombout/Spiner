package nl.mikero.spiner.frontend.exception;

import nl.mikero.spiner.frontend.FxmlLoadFailedException;
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

    @Test(expected = IllegalArgumentException.class)
    public void construct_EmptyFileName_ThrowsIllegalArgumentException() {
        // Arrange

        // Act
        new FxmlLoadFailedException("", new Exception());

        // Assert
    }
}
