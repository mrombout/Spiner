package nl.mikero.spiner.core.transformer.latex.model.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhitelineCommandTest {
    @Test
    public void toString_DefaultConstruction_ReturnsWhitelineCommand() {
        // Arrange
        WhitelineCommand command = new WhitelineCommand();

        // Act
        String result = command.toString();

        // Assert
        assertEquals("", result);
    }
}
