package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Command;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParametersTest {
    @Test(expected = NullPointerException.class)
    public void constructor_NullParent_ThrowsNullPointerException() {
        // Arrange

        // Act
        Parameters result = new Parameters(null);

        // Assert
    }

    @Test
    public void toString_NoParameters_ReturnsEmptyString() {
        // Arrange
        Parameters parameters = new Parameters(new AbstractCommand("dummy"));

        // Act
        String result = parameters.toString();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void toString_OneParameters_ReturnsValidOneParameter() {
        // Arrange
        Parameters parameters = new Parameters(new AbstractCommand("dummy")).add("parameter1");

        // Act
        String result = parameters.toString();

        // Assert
        assertEquals("{parameter1}", result);
    }

    @Test
    public void toString_TwoParameters_ReturnsValidTwoParameters() {
        // Arrange
        Parameters parameters = new Parameters(new AbstractCommand("dummy")).add("parameter1").add("parameter2");

        // Act
        String result = parameters.toString();

        // Assert
        assertEquals("{parameter1}{parameter2}", result);
    }

    @Test
    public void and_ValidParent_ReturnsParent() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");
        Parameters parameters = new Parameters(abstractCommand);

        // Act
        Command result = parameters.and();

        // Assert
        assertEquals(abstractCommand, result);
    }

    @Test
    public void done_ValidParent_ReturnsParent() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");
        Parameters parameters = new Parameters(abstractCommand);

        // Act
        Command result = parameters.done();

        // Assert
        assertEquals(abstractCommand, result);
    }
}
