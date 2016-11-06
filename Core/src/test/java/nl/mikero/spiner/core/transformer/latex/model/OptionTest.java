package nl.mikero.spiner.core.transformer.latex.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullNameAndValue_ThrowsNullPointerException() {
        // Arrange

        // Act
        Option option = new Option(null, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void constructor_NullName_ThrowsNullPointerException() {
        // Arrange

        // Act
        Option option = new Option(null, "value");

        // Assert
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_EmptyName_ThrowsIllegalArgumentException() {
        // Arrange

        // Act
        Option option = new Option("", null);

        // Assert
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_EmptyValue_ThrowsIllegalArgumentException() {
        // Arange

        // Act
        Option option = new Option("name", "");

        // Assert
    }

    @Test
    public void toString_NoValue_ReturnsNameOnly() {
        // Arrange
        Option option = new Option("name", null);

        // Act
        String result = option.toString();

        // Assert
        assertEquals("name", result);
    }

    @Test
    public void toString_NameAndValue_ReturnsNameEqualsValue() {
        // Arrange
        Option option = new Option("name", "value");

        // Act
        String result = option.toString();

        // Assert
        assertEquals("name=value", result);
    }
}
