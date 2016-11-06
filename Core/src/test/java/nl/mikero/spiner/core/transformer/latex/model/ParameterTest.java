package nl.mikero.spiner.core.transformer.latex.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullValue_ThrowsNullPointerException() {
        // Arrange

        // Act
        Parameter parameter = new Parameter(null);

        // Assert
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_EmptyValue_ThrowsIllegalArgumentException() {
        // Arrange

        // Act
        Parameter parameter = new Parameter("");

        // Assert
    }

    @Test
    public void toString_WithValue_ReturnsValueInBraces() {
        // Arrange
        Parameter parameter = new Parameter("value");

        // Act
        String result = parameter.toString();

        // Assert
        assertEquals("{value}", result);
    }
}
