package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import nl.mikero.spiner.commandline.VersionService;

import java.io.PrintStream;

/**
 * Prints the version information.
 */
public class VersionCommand implements Command {
    @Parameter(names = {"--version"}, description = "Show version information.", help = true)
    private boolean isVersion = false;

    private final VersionService versionService;
    private final PrintStream printStream;

    /**
     * Constructs a new VersionCommand.
     *
     * @param versionService version service to use
     */
    @Inject
    public VersionCommand(final VersionService versionService) {
        this(versionService, System.out);
    }

    /**
     * Constructs a new VersionCommand.
     *
     * @param versionService version service to use
     * @param printStream print stream to print to
     */
    public VersionCommand(final VersionService versionService, final PrintStream printStream) {
        this.versionService = versionService;
        this.printStream = printStream;
    }

    /**
     * Prints the version information.
     */
    @Override
    public final void run() {
        printStream.println("Spiner " + versionService.get() + "\n" +
                "Copyright (C) 2015 Mike Rombout\n" +
                "License GPLv3+: GNU GPL isVersion 3 or later <http://gnu.org/licenses/gpl.html>\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extend permitted by law.\n");
    }

    /**
     * Returns wether the version parameter was given by the user.
     *
     * @return <code>true</code> if "--help" parameter was given by the user
     */
    public final boolean isVersionCommand() {
        return isVersion;
    }
}
