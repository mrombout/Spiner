package nl.mikero.spiner.commandline;

import com.beust.jcommander.JCommander;
import nl.mikero.spiner.commandline.command.*;

/**
 * Creates all available commands.
 */
public interface CommandFactory {
    /**
     * Creates a new {@link HelpCommand}.
     *
     * @param jCommander jcommander for help command to use
     * @return a new help command instance
     */
    HelpCommand createHelpCommand(JCommander jCommander);

    /**
     * Creates a new {@link VersionCommand}.
     *
     * @return a new version command instance
     */
    VersionCommand createVersionCommand();

    /**
     * Creates a new {@link TransformCommand}.
     *
     * @return a new transform command instance
     */
    TransformCommand createTransformCommand();
}
