package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class HelpCommand implements Command {
    @Parameter(names = {"--help"}, description = "Show help description.", help = true)
    public boolean help = false;

    private JCommander jCommander;

    @Inject
    public HelpCommand(@Assisted final JCommander jCommander) {
        this.jCommander = jCommander;
    }

    @Override
    public void run() {
        jCommander.usage();
    }
}
