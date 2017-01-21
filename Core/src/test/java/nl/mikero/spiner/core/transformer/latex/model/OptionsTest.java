package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Command;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionsTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullParent_ThrowsNullPointerException() {
        // Arrange

        // Act
        Options options = new Options(null);

        // Assert
    }

    @Test
    public void toString_NoOptions_ReturnsEmptyString() {
        // Arrange
        Options options = new Options(new AbstractCommand("dummy"));

        // Act
        String result = options.toString();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void toString_OneOption_ReturnsValidOneOption() {
        // Arrange
        Options options = new Options(new AbstractCommand("dummy")).add("option1");

        // Act
        String result = options.toString();

        // Assert
        assertEquals("[option1]", result);
    }

    @Test
    public void toString_TwoOptions_ReturnsValidTwoOptions() {
        // Arrange
        Options options = new Options(new AbstractCommand("dummy")).add("option1").add("option2");

        // Act
        String result = options.toString();

        // Assert
        assertEquals("[option1,option2]", result);
    }

    @Test
    public void and_ValidParent_ReturnsParent() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");
        Options options = new Options(abstractCommand);

        // Act
        Command result = options.and();

        // Assert
        assertEquals(abstractCommand, result);
    }

    @Test
    public void done_ValidParent_ReturnsParent() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");
        Options options = new Options(abstractCommand);

        // Act
        Command result = options.done();

        // Assert
        assertEquals(abstractCommand, result);
    }
}
