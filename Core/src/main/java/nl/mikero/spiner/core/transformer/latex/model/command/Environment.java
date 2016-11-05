package nl.mikero.spiner.core.transformer.latex.model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Environment extends AbstractCommand {
    private final String environment;
    private final List<Command> commands;

    public Environment(String environment) {
        super("begin");

        this.environment = environment;
        this.commands = new ArrayList<>();

        parameters().add(environment);
    }

    public Environment addCommand(Command command) {
        Objects.requireNonNull(command);
        this.commands.add(command);

        return this;
    }
}
