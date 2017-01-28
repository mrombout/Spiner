package nl.mikero.spiner.core.transformer.latex.model.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvironmentTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullEnvironment_ThrowsNullPointerException() {
        // Arrange

        // Act
        Environment environment = new Environment(null);

        // Arrange
    }

    @Test
    public void toString_NoCommands_BeginsAndEndsEnvironment() {
        // Arrange
        Environment environment = new Environment("dummy");

        // Act
        String result = environment.toString();

        // Arrange
        assertEquals("\\begin{dummy}\n\n\\end{dummy}", result);
    }

    @Test
    public void toString_OneCommand_EnvironmentContainsSingleCommand() {
        // Arrange
        Environment environment = new Environment("dummy");
        environment.addCommand(new BasicCommand("command1"));

        // Act
        String result = environment.toString();

        // Arrange
        assertEquals("\\begin{dummy}\n\t\\command1\n\\end{dummy}", result);
    }

    @Test
    public void toString_TwoCommands_EnvironmentContainsTwoCommands() {
        // Arrange
        Environment environment = new Environment("dummy");
        environment.addCommand(new BasicCommand("command1"));
        environment.addCommand(new BasicCommand("command2"));

        // Act
        String result = environment.toString();

        // Arrange
        assertEquals("\\begin{dummy}\n\t\\command1\n\t\\command2\n\\end{dummy}", result);
    }

    @Test(expected = NullPointerException.class)
    public void addCommand_NullCommand_ThrowsNullPointerException() {
        // Arrange
        Environment environment = new Environment("dummy");

        // Act
        environment.addCommand(null);

        // Assert
    }

    @Test
    public void addCommand_Command_AddsToContainer() {
        // Arrange
        Environment environment = new Environment("dummy");
        BasicCommand command = new BasicCommand("command1");

        // Act
        environment.addCommand(command);

        // Assert
        assertEquals(command, environment.getContainer().getCommands().get(0));
    }
}
