package nl.mikero.spiner.commandline.goal;

import nl.mikero.spiner.commandline.annotation.DefineOption;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

@DefineOption(
    opt = "w",
    longOpt = "watch",
    description = "watch for Twine files and automatically transform to format"
)
public class WatchGoal implements Goal {
    @Override
    public void execute(CommandLine cmd, Options options) {

    }
}
