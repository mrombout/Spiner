package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class HelpCommand implements Command {
    @Parameter(names = {"--help"}, description = "Show help description.", help = true)
    private boolean help = false;

    private JCommander jCommander;

    /**
     * Constructs a new HelpCommand.
     *
     * @param jCommander configured JCommander to display usage for
     */
    @Inject
    public HelpCommand(@Assisted final JCommander jCommander) {
        this.jCommander = jCommander;
    }

    /**
     * Displays the usage in the console.
     */
    @Override
    public void run() {
        jCommander.usage();
    }
}
