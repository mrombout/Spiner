package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.Options;
import nl.mikero.spiner.core.transformer.latex.model.Parameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicCommandTest {
    @Test
    public void options_DefaultConstruction_ReturnsOptions() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy");

        // Act
        Options result = basicCommand.options();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void parameters_DefaultConstruction_ReturnsParameters() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy");

        // Act
        Parameters result = basicCommand.parameters();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void toString_DefaultConstruction_ReturnsCommandName() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy");

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy", result);
    }

    @Test
    public void toString_OneOption_ReturnsCommandNameWithOption() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy").options().add("option1").build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy[option1]", result);
    }

    @Test
    public void toString_TwoOptions_ReturnsCommandNameWithTwoOptions() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy").options().add("option1").add("option2").build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy[option1,option2]", result);
    }

    @Test
    public void toString_OneParameter_ReturnsCommandNameWithParameter() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy").parameters().add("parameter1").build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy{parameter1}", result);
    }

    @Test
    public void toString_TwoParameters_ReturnsCommandNAmeWithTwoParameters() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy").parameters().add("parameter1").add("parameter2").build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy{parameter1}{parameter2}", result);
    }

    @Test
    public void toString_OneOptionOneParameter_ReturnsCommandNameWithOptionAndParameter() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy")
                .parameters()
                    .add("parameter1")
                    .build()
                .options()
                    .add("option1")
                    .build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy[option1]{parameter1}", result);
    }

    @Test
    public void toString_TwoOptionsTwoParameters_ReturnsCommandNameWithTwoOptionsAndTwoParameters() {
        // Arrange
        BasicCommand basicCommand = new BasicCommand("dummy")
                .parameters()
                    .add("parameter1")
                    .add("parameter2")
                    .build()
                .options()
                    .add("option1")
                    .add("option2")
                    .build();

        // Act
        String result = basicCommand.toString();

        // Assert
        assertEquals("\\dummy[option1,option2]{parameter1}{parameter2}", result);
    }
}
