package nl.mikero.spiner.core.transformer.latex.model.command;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsePackageCommandTest {
    @Test
    public void toString_DefaultConstruction_ReturnsUsePackageCommand() {
        // Arrange
        UsePackageCommand command = new UsePackageCommand();

        // Act
        String result = command.toString();

        // Assert
        assertEquals("\\usepackage", result);
    }
}
