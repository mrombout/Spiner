package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.BasicCommand;
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
        Parameters parameters = new Parameters(new BasicCommand("dummy"));

        // Act
        String result = parameters.toString();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void toString_OneParameters_ReturnsValidOneParameter() {
        // Arrange
        Parameters parameters = new Parameters(new BasicCommand("dummy")).add("parameter1");

        // Act
        String result = parameters.toString();

        // Assert
        assertEquals("{parameter1}", result);
    }

    @Test
    public void toString_TwoParameters_ReturnsValidTwoParameters() {
        // Arrange
        Parameters parameters = new Parameters(new BasicCommand("dummy")).add("parameter1").add("parameter2");

        // Act
        String result = parameters.toString();

        // Assert
        assertEquals("{parameter1}{parameter2}", result);
    }

    @Test
    public void build_ValidParent_ReturnsParent() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy");
        Parameters parameters = new Parameters(basicCommand);

        // Act
        Command result = parameters.build();

        // Assert
        assertEquals(basicCommand, result);
    }
}
