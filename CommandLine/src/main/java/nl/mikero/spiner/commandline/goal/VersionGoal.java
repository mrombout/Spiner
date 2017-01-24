package nl.mikero.spiner.commandline.goal;

import nl.mikero.spiner.commandline.annotation.DefineOption;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

@DefineOption(
    opt = "v",
    longOpt = "version",
    description = "output version information and exit"
)
public class VersionGoal implements Goal {
    @Override
    public void execute(CommandLine cmd, Options options) {
        System.out.println("Spiner 0.0.1");
        System.out.println("Copyright (C) 2015 Mike Rombout");
        System.out.println("License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>");
        System.out.println("This is free software: you are free to change and redistribute it.");
        System.out.println("There is NO WARRANTY, to the extend permitted by law.");
    }
}
