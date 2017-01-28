package nl.mikero.spiner.commandline;

import com.beust.jcommander.MissingCommandException;
import nl.mikero.spiner.commandline.command.HelpCommand;
import nl.mikero.spiner.commandline.command.TransformCommand;
import nl.mikero.spiner.commandline.command.VersionCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CommandLineApplicationTest {
    private CommandLineApplication application;

    private CommandFactory mockCommandFactory;
    private HelpCommand mockHelpCommand;
    private VersionCommand mockVersionCommand;
    private TransformCommand mockTransformCommand;

    @Before
    public void setUp() {
        mockCommandFactory = Mockito.mock(CommandFactory.class);

        mockHelpCommand = Mockito.mock(HelpCommand.class);
        Mockito.when(mockCommandFactory.createHelpCommand(Mockito.any())).thenReturn(mockHelpCommand);

        mockVersionCommand = Mockito.mock(VersionCommand.class);
        Mockito.when(mockCommandFactory.createVersionCommand()).thenReturn(mockVersionCommand);

        mockTransformCommand = Mockito.mock(TransformCommand.class);
        Mockito.when(mockCommandFactory.createTransformCommand()).thenReturn(mockTransformCommand);

        application = new CommandLineApplication(mockCommandFactory);
    }

    @Test
    public void execute_NoArguments_RunHelpCommand() {
        // Arrange

        // Act
        application.execute(new String[] { });

        // Assert
        Mockito.verify(mockHelpCommand, Mockito.times(1)).run();
    }

    @Test
    public void execute_HelpParameter_RunHelpCommand() {
        // Arrange

        // Act
        application.execute(new String[] { "--help" });

        // Assert
        Mockito.verify(mockHelpCommand, Mockito.times(1)).run();
    }

    @Test
    public void execute_VersionParameter_RunVersionCommand() {
        // Arrange

        // Act
        application.execute(new String[] { "--version" });

        // Assert
        Mockito.verify(mockVersionCommand, Mockito.times(1)).run();
    }

    @Test(expected = MissingCommandException.class)
    public void execute_InvalidCommand_Unknown() {
        // Arrange

        // Act
        application.execute(new String[] { "thisdoesnotexist" });

        // Assert
    }
}
