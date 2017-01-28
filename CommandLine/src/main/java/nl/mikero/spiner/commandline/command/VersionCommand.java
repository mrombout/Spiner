package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import nl.mikero.spiner.commandline.service.GradleVersionService;
import nl.mikero.spiner.commandline.service.VersionService;

import java.io.PrintStream;

public class VersionCommand implements Command {
    private VersionService versionService;
    private PrintStream printStream;

    @Inject
    public VersionCommand(VersionService versionService) {
        this.versionService = versionService;
        this.printStream = System.out;
    }

    public VersionCommand(VersionService versionService, PrintStream printStream) {
        this(versionService);
        this.printStream = printStream;
    }

    @Parameter(names = {"--version"}, description = "Show version description.", help = true)
    public boolean version = false;

    @Override
    public void run() {
        printStream.println("Spiner " + versionService.get() + "\n" +
                "Copyright (C) 2015 Mike Rombout\n" +
                "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extend permitted by law.\n");
    }
}
