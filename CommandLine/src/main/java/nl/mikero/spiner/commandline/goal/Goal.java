package nl.mikero.spiner.commandline.goal;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface Goal {
    void execute(CommandLine cmd, Options option);
}
