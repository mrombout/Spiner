package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.Parameter;

public class VersionCommand implements Command {
    @Parameter(names = {"--version"}, description = "Show version description.", help = true)
    public boolean version = false;

    @Override
    public void run() {
        System.out.println("Spiner 0.0.1\n" +
                "Copyright (C) 2015 Mike Rombout\n" +
                "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extend permitted by law.\n");
    }
}
