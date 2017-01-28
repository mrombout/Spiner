package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import nl.mikero.spiner.commandline.service.VersionService;

import java.io.PrintStream;

public class VersionCommand implements Command {
    @Parameter(names = {"--version"}, description = "Show version description.", help = true)
    private boolean isVersion = false;

    private final VersionService versionService;
    private final PrintStream printStream;

    @Inject
    public VersionCommand(final VersionService versionService) {
        this(versionService, System.out);
    }

    public VersionCommand(final VersionService versionService, final PrintStream printStream) {
        this.versionService = versionService;
        this.printStream = printStream;
    }

    @Override
    public void run() {
        printStream.println("Spiner " + versionService.get() + "\n" +
                "Copyright (C) 2015 Mike Rombout\n" +
                "License GPLv3+: GNU GPL isVersion 3 or later <http://gnu.org/licenses/gpl.html>\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extend permitted by law.\n");
    }

    public boolean isVersionCommand() {
        return isVersion;
    }
}
