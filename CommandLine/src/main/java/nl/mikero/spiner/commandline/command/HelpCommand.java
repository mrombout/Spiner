package nl.mikero.spiner.commandline.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Prints spiners help information to the console.
 */
public class HelpCommand implements Command {
    @Parameter(names = {"--help"}, description = "Show help description.", help = true)
    private boolean help = false;

    private final JCommander jCommander;

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

    /**
     * Returns wether the help parameter what given by the user.
     *
     * @return <code>true</code> if "--help" parameter was given by the user
     */
    public final boolean isHelpCommand() {
        return help;
    }
}
