package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.BasicCommand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LatexContainerTest {
    @Test
    public void toString_NoCommands_ReturnsEmptyString() {
        // Arrange
        LatexContainer container = new LatexContainer();

        // Act
        String result = container.toString();

        // Assert
        assertEquals("", result);
    }

    @Test
    public void toString_OneCommand_ReturnsOneCommand() {
        // Arrange
        LatexContainer container = new LatexContainer();

        BasicCommand command1 = new BasicCommand("command1");
        container.addCommand(command1);

        // Act
        String result = container.toString();

        // Assert
        assertEquals("\\command1", result);
    }

    @Test
    public void toString_TwoCommands_ReturnsTwoCommandsOnePerLine() {
        // Arrange
        LatexContainer container = new LatexContainer();

        BasicCommand command1 = new BasicCommand("command1");
        container.addCommand(command1);

        BasicCommand command2 = new BasicCommand("command2");
        container.addCommand(command2);

        // Act
        String result = container.toString();

        // Assert
        assertEquals("\\command1\n\\command2", result);
    }

    @Test(expected = NullPointerException.class)
    public void addCommand_NullCommand_ThrowsNullPointerException() {
        // Arrange
        LatexContainer container = new LatexContainer();

        // Act
        container.addCommand(null);

        // Assert
    }
}
