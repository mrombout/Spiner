package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.Options;
import nl.mikero.spiner.core.transformer.latex.model.Parameters;

public class AbstractCommand implements Command {
    private final String commandName;
    private final Options options;
    private final Parameters parameters;

    public AbstractCommand(final String commandName) {
        this.commandName = commandName;
        this.options = new Options(this);
        this.parameters = new Parameters(this);
    }

    public Options options() {
        return options;
    }

    public Parameters parameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return String.format("\\%s%s%s", commandName, options, parameters);
    }
}
