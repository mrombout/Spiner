package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.Options;
import nl.mikero.spiner.core.transformer.latex.model.Parameters;

import java.util.Optional;

public class AbstractCommand implements Command {
    private final String commandName;
    private final Options options;
    private final Parameters parameters;

    public AbstractCommand(String commandName) {
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
}
