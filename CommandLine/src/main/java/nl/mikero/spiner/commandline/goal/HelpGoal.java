package nl.mikero.spiner.commandline.goal;

import nl.mikero.spiner.commandline.annotation.DefineOption;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

@DefineOption(
        opt = "h",
        longOpt = "help",
        description = "display this help and exit"
)
public class HelpGoal implements Goal {
    @Override
    public void execute(CommandLine cmd, Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("spiner [--help | --version] [[--watch] [-f <epub|latex>] -i <file|directory> [-o <file|directory>]]", options);
    }
}
