package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.Options;
import nl.mikero.spiner.core.transformer.latex.model.Parameters;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractCommandTest {
    @Test
    public void options_DefaultConstruction_ReturnsOptions() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");

        // Act
        Options result = abstractCommand.options();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void parameters_DefaultConstruction_ReturnsParameters() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");

        // Act
        Parameters result = abstractCommand.parameters();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void toString_DefaultConstruction_ReturnsCommandName() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy");

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy", result);
    }

    @Test
    public void toString_OneOption_ReturnsCommandNameWithOption() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy").options().add("option1").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy[option1]", result);
    }

    @Test
    public void toString_TwoOptions_ReturnsCommandNameWithTwoOptions() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy").options().add("option1").add("option2").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy[option1,option2]", result);
    }

    @Test
    public void toString_OneParameter_ReturnsCommandNameWithParameter() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy").parameters().add("parameter1").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy{parameter1}", result);
    }

    @Test
    public void toString_TwoParameters_ReturnsCommandNAmeWithTwoParameters() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy").parameters().add("parameter1").add("parameter2").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy{parameter1}{parameter2}", result);
    }

    @Test
    public void toString_OneOptionOneParameter_ReturnsCommandNameWithOptionAndParameter() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy").parameters().add("parameter1").and().options().add("option1").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy[option1]{parameter1}", result);
    }

    @Test
    public void toString_TwoOptionsTwoParameters_ReturnsCommandNameWithTwoOptionsAndTwoParameters() {
        // Arrange
        AbstractCommand abstractCommand = new AbstractCommand("dummy")
                .parameters().add("parameter1").add("parameter2").and()
                .options().add("option1").add("option2").done();

        // Act
        String result = abstractCommand.toString();

        // Assert
        assertEquals("\\dummy[option1,option2]{parameter1}{parameter2}", result);
    }
}
