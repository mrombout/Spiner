package nl.mikero.spiner.commandline.command;

import nl.mikero.spiner.commandline.service.VersionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

public class VersionCommandTest {
    private VersionCommand command;

    private VersionService mockVersionService;
    private PrintStream mockPrintStream;

    @Before
    public void setUp() {
        mockVersionService = Mockito.mock(VersionService.class);
        mockPrintStream = Mockito.mock(PrintStream.class);
        command = new VersionCommand(mockVersionService, mockPrintStream);
    }

    @Test
    public void run_VersionIs123456789_ReturnsVersion123456789() {
        // Arrange
        String expectedVersion = "123456789";
        Mockito.when(mockVersionService.get()).thenReturn(expectedVersion);

        // Act
        command.run();

        // Assert
        Mockito.verify(mockPrintStream).println(Mockito.contains(expectedVersion));
    }
}
