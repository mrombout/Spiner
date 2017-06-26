package nl.mikero.spiner.core.transformer.latex.model.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RawTextCommandTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullText_ThrowsNullPointerException() {
        // Arrange

        // Act
        RawTexCommand command = new RawTexCommand(null);

        // Assert
    }

    @Test
    public void toString_SimpleText_ReturnsText() {
        // Arrange
        String text = "dummy";
        RawTexCommand command = new RawTexCommand(text);

        // Act
        String result = command.toString();

        // Assert
        assertEquals(text, result);
    }
}
