package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.Options;
import nl.mikero.spiner.core.transformer.latex.model.Parameters;

/**
 * A basic LaTeX command.
 */
public class BasicCommand implements Command {
    private final String commandName;
    private final Options options;
    private final Parameters parameters;

    /**
     * Constructs a new BasicCommand.
     *
     * @param commandName name of the LaTeX command
     */
    public BasicCommand(final String commandName) {
        this.commandName = commandName;
        this.options = new Options(this);
        this.parameters = new Parameters(this);
    }

    /**
     * Returns the {@link Options} of this command.
     *
     * @return options
     */
    public final Options options() {
        return options;
    }

    /**
     * Returns the {@link Parameters} of this command.
     *
     * @return parameters
     */
    public final Parameters parameters() {
        return parameters;
    }

    /**
     * Returns a valid LaTeX string representation of this command.
     *
     * @return valid LaTeX representation of this command
     */
    @Override
    public final String toString() {
        return String.format("\\%s%s%s", commandName, options, parameters);
    }
}
